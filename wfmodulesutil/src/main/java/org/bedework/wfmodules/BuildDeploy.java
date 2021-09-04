/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.wfmodules;

import org.bedework.util.deployment.Args;
import org.bedework.util.deployment.Utils;
import org.bedework.util.maven.deploy.ModulePom;

import org.apache.maven.plugin.logging.SystemStreamLog;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.bedework.util.maven.deploy.ModulePom.WfModule;

/**
 * User: mike Date: 6/12/21 Time: 22:52
 */
public class BuildDeploy {
  private final Utils utils;
  private String rootDir;
  private boolean details;

  // True to generate download and install commands
  private boolean downloads;

  // For downloads the set of projects we need to install
  final Set<String> downloadProjects = new TreeSet<>();

  final List<String> warnings = new ArrayList<>();

  final Set<String> wfrefs = new TreeSet<>();

  /* List of modules we want to deploy. Usually the top-level ear modules
   */
  final Set<String> deployList = new TreeSet<>();

  private static class ModuleInfo {
    private final String project;

    private final WfModule module;

    private ModuleInfo(final String project,
                       final WfModule module) {
      this.project = project;
      this.module = module;
    }

    public String getProject() {
      return project;
    }

    public WfModule getModule() {
      return module;
    }
  }
  private final Map<String, ModuleInfo> modules = new HashMap<>();

  private BuildDeploy() {
    utils = new Utils(new SystemStreamLog());
  }

  public class PomVisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile(final Path file,
                                     final BasicFileAttributes attributes)
            throws IOException {
      if(!attributes.isRegularFile()) {
        return FileVisitResult.CONTINUE;
      }

      if (!file.getFileName().toString().equals("pom.xml")) {
        return FileVisitResult.CONTINUE;
      }

      try {
        final ModulePom mp = new ModulePom(utils, file, true);
        final var project = file.getParent().getFileName().toString();

        for (final WfModule module: mp.getModules()) {
          if (modules.get(module.getModuleName()) != null) {
            warn("Duplicate module " + module.getModuleName());
          } else {
            modules.put(module.getModuleName(),
                        new ModuleInfo(project, module));
          }
        }
      } catch (final Throwable t) {
        throw new IOException(t);
      }
      return FileVisitResult.CONTINUE;
    }
  }

  private void generate() {
    final PomVisitor pv = new PomVisitor();
    try {
      Files.walkFileTree(Paths.get(rootDir), pv);
    } catch (final Throwable t) {
      utils.error(t.getMessage());
      t.printStackTrace();
    }

    for (final String key: new TreeSet<>(modules.keySet())) {
      final ModuleInfo module = modules.get(key);

      final var moduleName = module.getModule().getModuleName();
      final var project = module.getProject();
      final var projectMunged = projToModule(project);

      if (details) {
        utils.info("Module: " + moduleName);
        utils.info("Project: " + project);
      }

      if (!projectMunged.equals(moduleName)) {
        warn("******** Problem project: " + project);
      }

      if (details) {
        utils.info("   --> ");
      }

      for (final String md: module.getModule().getDependencies()) {
        if (details) {
          utils.info("       " + md);
        }

        if (modules.get(md) == null) {
          if (!md.startsWith("org.bedework.")) {
            wfrefs.add(md);
          } else {
            warn("Unsatisfied dependency for " + moduleName +
                         ": " + md);
          }
        }
      }

      if (details) {
        utils.info(" ");
      }
    }

    if (!warnings.isEmpty()) {
      utils.warn("************* Warnings:");
      for (final String s: warnings) {
        utils.warn(s);
      }
    }

    if (!wfrefs.isEmpty()) {
      utils.info("************* External references:");
      for (final String s: wfrefs) {
        utils.info(s);
      }
    }
  }

  private void processDeploy(final String moduleName) {
    final String project = moduleToProj(moduleName);

    if (downloadProjects.contains(project)) {
      warn("Already present in downloads: " + project);
      return;
    }
    downloadProjects.add(project);

    final ModuleInfo module = modules.get(moduleName);

    if (module == null) {
      warn("Unable to find module " + moduleName);
      return;
    }

    if (!project.equals(module.getProject())) {
      warn("problem project name " + project + ": " + module.getProject());
    }

    for (final String md: module.getModule().getDependencies()) {
      if (modules.get(md) == null) {
        if (md.startsWith("org.bedework.")) {
          warn("Unsatisfied dependency for " + moduleName +
                       ": " + md);
        }

        continue;
      }

      processDeploy(md);
    }
  }

  private String projToModule(final String project) {
    return project.replace("bw-wfmodules-", "org.bedework.").
            replace("-", ".");
  }

  private String moduleToProj(final String module) {
    return module.replace("org.bedework.", "bw-wfmodules-").
            replace(".", "-");
  }

  private void warn(final String msg) {
    warnings.add(msg);
    utils.warn(msg);
  }

  private boolean process(final Args args) {
    while (args.more()) {
      if (args.ifMatch("")) {
        continue;
      }

      if (args.ifMatch("--root")) {
        rootDir = args.next();
        continue;
      }

      if (args.ifMatch("--details")) {
        details = true;
        continue;
      }

      if (args.ifMatch("--downloads")) {
        downloads = true;
        continue;
      }

      if (args.ifMatch("--deploy")) {
        deployList.add(args.next());
        continue;
      }

      usage("Unrecognized option: " + args.current());
      return false;
    }

    if (rootDir == null) {
      usage("Must specify --root");
      return false;
    }

    generate();

    if (!deployList.isEmpty()) {
      for (final String deploy: deployList) {
        processDeploy(deploy);
      }

      utils.info("Downloads:");
      for (final String d: downloadProjects) {
        utils.info(d);
      }
    }

    return true;
  }

  private void usage(final String error_msg) {
    if (error_msg != null) {
      utils.info(error_msg);
    }

    utils.info("Usage: xxx [options]\n" +
                       "Options:\n" +
                       "    --deploy  <module> to deploy" +
                       "    --details to list modules and dependencies" +
                       "    --root    <path> " +
                       "              Directory containing wfmodule sources.");
  }

  public static void main(final String[] args) {
    final BuildDeploy bd = new BuildDeploy();

    bd.process(new Args(args));
  }
}