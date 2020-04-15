scalaVersion := "2.12.11"

lazy val `spendings-manager` = (project in file("."))
  .settings(name := "Spendings Manager")

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false

fork := true

outputStrategy := Some(StdoutOutput)

connectInput := true

libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.3" % Test
libraryDependencies += "org.specs2" %% "specs2-mock" % "4.8.3" % Test
libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.18"
