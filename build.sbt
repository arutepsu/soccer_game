import sbt.Keys.connectInput

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"
lazy val root = (project in file("."))
  .settings(
    name := "SoccerGame",
    connectInput / run := true,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
  )