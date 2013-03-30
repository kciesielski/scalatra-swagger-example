import sbt._
import Keys._

object Plugins extends Build {
  lazy val plugins = Project(
    "plugins",
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      libraryDependencies += ("com.github.siasia" %% "xsbt-web-plugin" % "0.12.0-0.2.11.1"),
      addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.2.0")))

}
