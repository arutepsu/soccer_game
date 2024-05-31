import sbt.Keys.connectInput

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
    // Configure sbt-coveralls plugin
    coverallsToken := Some(sys.env.get("COVERALLS_REPO_TOKEN")),
    coverallsServiceName := Some("github")
  )
