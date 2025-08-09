# Change Log

This project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased (2.1.0-SNAPSHOT)

## [2.0.0] - 2025-07-10
This release was focussed on getting in to the jakarta world.
This required many dependencies to be updated or added.

### Added
- A bunch of modules for new dependencies.

### Removed
- All references to exchange.xml. It wasn't used and probably out of date.

### Changed
- This is the first jakarta release so many dependencies were changed to the appropriate jakarta version. 
- Some code moved into new bw-wfmodules-base module. Added a bunch of dependencies.
- Wildfly version. We are some versions in to the post-jakarta world, so dependencies are made consistent with the release we are using (36.1)
- Updated struts2: The jakarta move also required us to update to a later - incompatible - version of struts2 which enforces package and namespace.
- Hibernate - managed to update so this works. 
- Add support for classifier - all for ehcache 

### Removed
- Deleted module util-cli 
- Remove last of spring support 

## [1.0.8] - 2025-02-06
This release was focussed on preparing for the move to the jakarta world. Mostly this involved upgrading to the latest struts2 6.x release and changing the database related code to be more aligned with jpf.
This required many dependencies to be updated or added.

### Added
- A bunch of modules for new dependencies.
- Add eventreg web service

### Changed 
- Upgrade to struts 6.7.0 in advance of move to jakarta 
- Correct some dependencies 
- More major refactoring in advance of move to jakarta: Split out most of the non-orm specific code into a new module. 
- Largely moving util-hibernate into the new bw-database module - including refactor of the wildfly module deployment. 
- Move response classes, exceptions and ToString into bw-base module. Resquirs new dependencies.
- Changes for splitting up carddav and deploying a war rather than a ear 

## [1.0.8] - 2024-12-02
### Changed
- Fix a dependency

## [1.0.7] - 2024-12-01
### Changed
- Needed to add a dependency

## [1.0.6] - 2024-11-30
### Changed
- Move jdkim and split into library and api 
- Refactor bw-cli into a multi-module project. Update library versions.

## [1.0.5] - 2024-04-03
### Added
- Add hawtio 
- Add json and schemafororg modules 
- Struts 2 client modules 
- webcache modules. 

### Changed
- Move calws-soap into separate project from caldav or we get circular dependency 
- Next stage in removing the bw-xml module.  
  - Move synch xml  
  - removed the xml modules from the build. 
  - icalendar schema moved into its own module  
  - Many modules updated to refer to it  
  - calws schema moved into caldav  
- Convert deployed timezone server, notifier, synch and selfreg to war 
- Fix hibernate module so schema works 
- Rearrange modules to more closely represent deployed structure 

### Removed
- Remove unused module 
- Remove bedework slf4j module - use jboss module 
- Fix bw-cli module 
- Remove last of struts1. 

## [1.0.4] - 2022-06-22
- Allow specification of optional module dependencies 
- Reinstate code removed in error - required for notifications in UI 
iml files 
- Add some rw modules to the ro-war module as ConfigurationsImpl requires them. Will remove when that module is split up. 

## [1.0.3] - 2021-02-13
- Intervening releases failed
- Updated parent version

## [1.0.0] - 2021-02-12
- First release 
