import sbtassembly.Plugin._
import AssemblyKeys._

name := "flume-kafka-sink"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += "local maven" at ("file://%s/.m2/repository" format Path.userHome.absolutePath)

resolvers += "Hadoop Releases" at "https://repository.cloudera.com/content/repositories/releases/"

resolvers += "Cloudera Repos" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

libraryDependencies += "org.apache.flume" % "flume-ng-core" % "1.4.0-cdh4.5.0" % "provided"

libraryDependencies += "org.apache.flume" % "flume-ng-sdk" % "1.4.0-cdh4.5.0" % "provided"

libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.8.1.1"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"

ivyXML :=
  <dependencies>
    <exclude org="org.apache.thrift" module="libthrift"/>
    <exclude org="com.sun.jdmk" module="jmxtools" />
    <exclude org="com.sun.jmx" module="jmxri" />
    <exclude org="javax.jms" module="jms" />
  </dependencies>

assemblySettings
