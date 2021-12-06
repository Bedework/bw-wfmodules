# bw-wfmodules
No source - will deploy bedework modules to wildfly and create zipped version in maven central.

##The intent
The idea is to move all of the deployed jars out of teh deployed
ears (in the lib directory) and into jboss modules that have the
appropriate dependencies. The deployed ear will then depend on a
single top level module.

Most of this has gone OK - there is a spring-based application 
and that was difficult till I figured out the xml.

##Problems encountered
### instanceOf doesn't work as expected.
instanceOf only returns true if the class loaders are equal.
One of the modules is ical4J and I hada  test looking for an 
instance of a particular class. This failed and digging down I 
discovered this code:
