import sbt._
import Keys._
import scala._

object Resolvers {
  val customResolvers = Seq(
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
  )
}

object BuildSettings {

  import Resolvers._

  val buildSettings = Defaults.defaultSettings ++ Seq(

    organization := "org.kc",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.10.0",

    resolvers := customResolvers,

    scalacOptions += "-unchecked",
    classpathTypes ~= (_ + "orbit")
  )
}

object Dependencies {

  val scalatraVersion = "2.2.0"

  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val scalatraJson = "org.scalatra" %% "scalatra-json" % scalatraVersion
  val scalatraSwagger = "org.scalatra" %% "scalatra-swagger" % scalatraVersion

  val json4s = "org.json4s" %% "json4s-jackson" % "3.1.0"
  val swaggerCore = "com.wordnik" % "swagger-core_2.10.0" % "1.2.0"
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container"
  val scalatraStack = Seq(scalatra, scalatraJson, scalatraSwagger)

  // If the scope is provided;test, as in scalatra examples then gen-idea generates the incorrect scope (test).
  // As provided implies test, so is enough here.
  val servletApiProvided = "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "provided" artifacts (Artifact("javax.servlet", "jar", "jar"))
}

object ExampleAppBuild extends Build {

  import Dependencies._
  import BuildSettings._
  import com.github.siasia.WebPlugin.webSettings

  lazy val rest: Project = Project(
    "app",
    file("."),
    settings = buildSettings ++ webSettings ++
      Seq(libraryDependencies ++= Seq(swaggerCore, json4s, servletApiProvided)
        ++ scalatraStack
        ++ Seq(jetty, servletApiProvided))
  )
}
