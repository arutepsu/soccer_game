import sbt.Keys.connectInput
import sbt.plugins.JacocoSettings._
import CoverallsPlugin._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "SoccerGame",
    connectInput / run := true,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    jacoco.settings,
    jacocoCoverallsServiceName := "jenkins",
    jacocoCoverallsJobId := sys.env.get("BUILD_ID"), // If None, Coveralls sets its own job ID.
    jacocoCoverallsRepoToken := "<repo token on coveralls.io>"
  )
