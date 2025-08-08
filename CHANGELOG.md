# Release Notes

This project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased (2.1.0-SNAPSHOT)

## [2.0.0] - 2025-07-10
* First jakarta release
* Need latest commons.io. Also fix struts2 module dependencies 
* Delete module util-cli 
* Missing dependency 
* fix: bw-wfmodules-org/bw-wfmodules-apache/bw-wfmodules-struts/bw-wfmodules-struts2-core/pom.xml to reduce vulnerabilities snyk-bot 7/29/25, 03:21
* Fix eventreg dependency 
* Remove exchange xml 
* Remove hlc and llc modules 
* Add missing struts dependency 
* Add missing hibernate dependency 
* Add/move some exception handling into base. Add util method to list callers 
* A number of fixes to the eventreg war and methods. 
* Use icu classes for date formatting. java.util.DateFormat inserts characters like Narrow No-Break Space into result. 
* Add missing dependency 
* Fix some dependencies 
* Make a start on high and low level client work. Will move most of the higher level code out of what is currently the clients and actions into hlc. 
* Update for struts v7 - enforces package and namespace 
* Add a struts dependency 
* Add support for classifier - all for ehcache 
* Remove last of spring support 
* Fix up jolokia 
* Finally managed to get the galleon install to work. Disabled the undertow handlers build as it caused th ewrong version of jboss-threads to be included. 
* All modules now build in the jakarta world. 
* Update to struts2-core 7.0.0 
* Changes for orm initialisation. More modules building. 
 
## [1.0.8] - 2025-02-06
* Add missing module dependency 
* Upgrade to struts 6.7.0 in advance of move to jakarta 
* Correct some dependencies 
* More major refactoring in advance of move to jakarta: Split out most of the non-orm specific code into a new module. 
* Switch to use DbSession from bw-database. 
* Fix various pom issues after refactor 
* Mostly refactor to clear up naming and to prepare for factoring out common db code - essentially jpa code as against openjpa or hibernate. 
* Fix openjpa module 
* Add openjpa modules 
* Largely moving util-hibernate into the new bw-database module - including refactor of the wildfly module deployment. 
* Add start of openjpa implementation. Add new database exception and move db related exceptions into new package. Get rid of HibException and use above. Remove some db interface features we never used. 
* More fixes - missing dependencies and typos 
* Move response classes and ToString into bw-base module. 
* Move most of the exceptions into the new bw-base module. 
* Preparing for jakarta... Update parent to latest snapshot for all modules. Work to update hibernate second level cache to ehcache 3 + jcache. 
  * Update configs 
  * Update to new ehcache.xml 
  * Update deployed hibernate module 
* Fix dependencies and update web.xml for servlet changes 
* Add new admin application. Implement (partially) 1 of the actions. Add some changes to the util classes to facilitate 
* Split ticket into interface and implementation Make a start on a webadmin module Move context listener into the web module Rename web-common as war-common Complete ProcessRegInfo 
* Add eventreg web service 
* Changes for splitting up carddav and deployign a war rather than a ear 
* Changes for updated deployment of eventreg 
* Repackage category server 
* Repackage category server 
* Update with version 1.0.9-SNAPSHOT of bw-wfmodules 

## [1.0.8] - 2024-12-02
* Fix a dependency

## [1.0.7] - 2024-12-01
* Needed to add a dependency

## [1.0.6] - 2024-11-30
* Update to opensearch 2.18.0 
* Move jdkim and split into library and api 
* Update parent version 
* Missing dependency for mail module 
* Remove bogus property settings 
* Add missing spring dependency 
* fix: bw-wfmodules-org/bw-wfmodules-org-apache-jdkim-library/pom.xml to reduce vulnerabilities snyk-bot 7/23/24, 05:25
* Refactor bw-cli into a multi-module project. Update library versions. 
* Add missing dependency 
* Missing dependency for soap 
* Update module/project versions 
* fix: bw-wfmodules-org/bw-wfmodules-spring/bw-wfmodules-spring-ws-security/pom.xml to reduce vulnerabilities snyk-bot 4/14/24, 15:06

## [1.0.5] - 2024-04-03
* Split bw-calws-soap into 2 submodules - 1 for jar and one for zip. 
* Update parent version 
* Fix dependencies for bw-calws-soap 
* Move calws-soap into separate project from caldav or we get circular dependency 
* Next stage in removing the bw-xml module.  
  * Move synch xml  
  * Added feature packs for the 2 wsdl deployments  
  * Removed the xml feature pack from the build.  
  * removed the xml modules from the build. 
  * icalendar schema moved into its own module  
  * Many modules updated to refer to it  
  * calws schema moved into caldav  
  * feature pack update to deploy calws wsdls into bedework-content  
  * Fixed timezone server context 
* Fix deploy of timezone server as war 
* Convert deployed timezone server to war 
* Remove unused module 
* Turn selfreg into war 
* fix: bw-wfmodules-org/bw-wfmodules-spring/bw-wfmodules-spring-ws-security/pom.xml to reduce vulnerabilities snyk-bot 3/4/24, 22:59
* fix: bw-wfmodules-org/bw-wfmodules-org-apache-jdkim-library/pom.xml to reduce vulnerabilities snyk-bot 2/28/24, 19:37
* Add a couple of module dependencies - strictly unnecessary but didn't get jackson-core module otherwise. 
* Fix ical4j dependency 
* Make notifier a war only. Ear screws up classpath so hibernate doesn't work 
* Remove bedework slf4j module - use jboss module 
* Remove deleted module 
* Move logging into bw-util 
* fix: bw-wfmodules-org/bw-wfmodules-spring/bw-wfmodules-spring-ws-security/pom.xml to reduce vulnerabilities snyk-bot 11/24/23, 14:34
* Update to commons-fileupload 1.5 
* First pass at an undertow handler to check on system availability. Trying to dynamically react to changes in config without needing a restart. 
* fix: bw-wfmodules-org/bw-wfmodules-spring/bw-wfmodules-spring-ws-security/pom.xml to reduce vulnerabilities snyk-bot 11/2/23, 00:20
* Add code to query opensearch to get scroll context info. 
* fix: bw-wfmodules-org/bw-wfmodules-spring/bw-wfmodules-spring-ws-security/pom.xml to reduce vulnerabilities snyk-bot 10/3/23, 15:54
* Deploy synch service as a war. The ear version could not access hibernate mappings. 
* Add configuration for mysql 
* fix: bw-wfmodules-org/bw-wfmodules-spring/bw-wfmodules-spring-ws-security/pom.xml to reduce vulnerabilities snyk-bot 7/17/23, 15:42
* Fix restore of data. Needed to supply classloader 
* Fix hibernate module so schema works 
* Fix bw-cli module 
* Move Indexing mbean out of opensearch package to remove unnecessary dependencies. 
* Fix broken pom 
* fix: bw-wfmodules-org/bw-wfmodules-apache/bw-wfmodules-struts/bw-wfmodules-struts2-core/pom.xml to reduce vulnerabilities snyk-bot 2/21/23, 23:01
* fix: upgrade org.glassfish.jaxb:txw2 from 2.3.1 to 2.3.7 snyk-bot 2/17/23, 23:45
* fix: upgrade net.bytebuddy:byte-buddy from 1.12.9 to 1.12.22 snyk-bot 2/17/23, 23:45
* fix: upgrade ognl:ognl from 3.1.29 to 3.3.4 snyk-bot 2/17/23, 21:53
* fix: upgrade org.apache.wss4j:wss4j-ws-security-dom from 2.4.0 to 2.4.1 snyk-bot 2/17/23, 21:25
* fix: upgrade net.sf.ehcache:ehcache from 2.10.6 to 2.10.9.2 snyk-bot 2/17/23, 21:25
* Build bw-logs as the app for log processing. Add new script and update script to use that module. 
* Fix pom hierarchy 
* Intellij files were deleted 
* Rearrange modules to more closely represent deployed structure 
* Add support for the Saxon XSLT support 
* Upgrade to opensearch 2.1.0 
* Parent: Add jboss repositories. Remove last of struts1. Add hawtio Updated some versions. 
* Wrong dependency for struts2 module 
* Add hawtio 
* BwCalendar extension now works - implemented some indexing and system values - also list-indexes operation. 
* Upgrade to hibernate 5.6 
* Revert to regular hibernate 
* Remove struts-1 module 
* Fix artifact name 
* Add hibernate module as an optional dependency. 
* Add json and schemafororg modules 
* Library version updates 
* Removed struts 1 support. 
* Struts 2 client modules 
* Struts 2 needs log4j.logging 
* Add javax.servlet.api dependency for freemarker 
* iml files 
* Remove last dependencies on struts taglibs in jsp. 
* Add a select tag to use instead of struts html tag 
* Fix dependencies on parent to be consistent. 
* Add a BwFormTag to proxy the struts tag - also set xhtml for struts 1 
* New bw tag to emit xprops. 
* Fix struts 2 module 
* Add qs reset scripts for demo versions 
* Add dependencies for struts2 and add a module 
* Update commons text version 
* Add struts as a dependency 
* Add lang3 as a dependency 
* Use apache library to filter - removes dependency on struts. 
* webcache modules. 

## [1.0.4] - 2022-06-22
* Allow specification of optional module dependencies 
* Reinstate code removed in error - required for notifications in UI 
iml files 
* Add some rw modules to the ro-war module as ConfigurationsImpl requires them. Will remove when that module is split up. 

## [1.0.3] - 2021-02-13
* Intervening releases failed
* Updated parent version

## [1.0.0] - 2021-02-12
* First release 
