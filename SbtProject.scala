import java.io.File

/**
* Script that creates an SBT project structure for a given project name.
* ex: scala SbtProject my-project
* @author dbalduini
*/

require(args.size == 1, "Please inform the Project Name")

def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
	val p = new java.io.PrintWriter(f)
	try {
		op(p)
	} finally {
		p.close()
	}
}

val project = args(0)

// Change to your organization default name
val organization = "io.react2"

val version = scala.util.Properties.scalaPropOrElse("version.number", "unknown")

// Adapt libraryDependencies to your needs
val build = s"""
name := "$project"

organization := "$organization"

version := "0.0.1-SNAPSHOT"

scalaVersion := "$version"

resolvers ++= Seq(
  "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype Releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe"  % "config"         % "1.2.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.1"  % "test"
)

"""

val plugins = """
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
"""

val properties = """
sbt.version=0.13.6
"""

val gitignore = """
logs
project/project
project/target
/target
tmp
.history
dist
/.idea
/*.iml
/out
/.idea_modules
/.classpath
/.project
/RUNNING_PID
/.settings
/.cache
"""

new File(project).mkdir()
println(s"[Created] $project directory")

// Create Structure
val structure = List(
    "project", 
    "src/main/scala", 
    "src/main/resources", 
    "src/test/scala", 
    "src/test/resources")

structure.map {new File(project, _).mkdirs()}
println(s"[Created] $project tree structure")

new File(project, "src/main/resources/application.conf")

// Write build.sbt file
printToFile(new File(project, "build.sbt")){pw => pw write build}
println("[Created] build.sbt")

// Write plugins file
printToFile(new File(project, "project/plugins.sbt")){ pw => pw write plugins}
println("[Created] project/plugins.sbt")

// Write build properties
printToFile(new File(project, "project/build.properties")){ pw => pw write properties}
println("[Created] project/build.properties")

printToFile(new File(project, ".gitignore")){ pw => pw write gitignore}
println("[Created] .gitignore")

println("All done. Bye.")
