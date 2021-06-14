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
import java.util.TreeSet;

import static org.bedework.util.maven.deploy.ModulePom.WfModule;

/**
 * User: mike Date: 6/12/21 Time: 22:52
 */
public class BuildDeploy {
  private final Utils utils;
  private String rootDir;

  final List<String> warnings = new ArrayList<>();

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
            final var warning = "Duplicate module " + module.getModuleName();
            warnings.add(warning);
            utils.warn(warning);
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
      final var projectMunged =
              project.replace("bw-wfmodules-", "org.bedework.").
                      replace("-", ".");

      utils.info("Module: " + moduleName);
      utils.info("Project: " + project);

      if (!projectMunged.equals(moduleName)) {
        final var warning = "******** Problem project: " + project;
        warnings.add(warning);
        utils.warn(warning);
      }

      utils.info("   --> ");
      for (final String md: module.getModule().getDependencies()) {
        utils.info("       " + md);
      }
      utils.info(" ");
    }

    if (!warnings.isEmpty()) {
      utils.warn("************* Warnings:");
      for (final String s: warnings) {
        utils.warn(s);
      }
    }
  }

  private boolean process(final Args args) {
    while (args.more()) {
      if (args.ifMatch("")) {
        continue;
      }
      if (args.ifMatch("--root")) {
        rootDir = args.next();
      } else {
        usage("Unrecognized option: " + args.current());
        return false;
      }
    }

    if (rootDir == null) {
      usage("Must specify --root");
      return false;
    }

    generate();

    return true;
  }

  private void usage(final String error_msg) {
    if (error_msg != null) {
      utils.info(error_msg);
    }

    utils.info("Usage: xxx [options]\n" +
                       "Options:\n" +
                       "    --root        Directory containing wfmodule sources.");
  }

  public static void main(final String[] args) {
    final BuildDeploy bd = new BuildDeploy();

    bd.process(new Args(args));
  }
}