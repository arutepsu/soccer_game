import sbt.Keys.connectInput

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
      name := "SoccerGame",
      connectInput / run := true,
      libraryDependencies ++= Seq(
          "org.scalactic" %% "scalactic" % "3.2.18",
          "org.scalatest" %% "scalatest" % "3.2.18" % Test,
          "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
          "org.scalafx" %% "scalafx" % "22.0.0-R33",
          "org.openjfx" % "javafx-controls" % "22.0.1",
      ),
      Test / testOptions += Tests.Argument("-oD") // Detailed output for debugging
  )