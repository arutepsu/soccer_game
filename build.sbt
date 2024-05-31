import sbt.Keys.connectInput
import coveralls.CoverallsPlugin.autoImport._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "SoccerGame",
    connectInput / run := true,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
    ),
    // Scoverage settings
    coverageEnabled := true,
    coverageFailOnMinimum := true,
    coverageHighlighting := true,
    // Coveralls settings
    coverallsToken := Some(sys.env.getOrElse("COVERALLS_REPO_TOKEN", "")),
    coverallsServiceName := Some("github"),
    coverallsServiceJobId := Some(sys.env.getOrElse("GITHUB_RUN_ID", ""))
  )
