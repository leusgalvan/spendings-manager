scalaVersion := "2.12.11"

lazy val `tdd-course` = (project in file("."))
  .settings(name := "TDD Course")

libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.3" % Test
libraryDependencies += "org.specs2" %% "specs2-mock" % "4.8.3" % Test
