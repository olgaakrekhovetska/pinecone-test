ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "pinecone-test"
  )

libraryDependencies ++= Seq("io.pinecone" % "pinecone-client" % "0.6.0")

//assembly settings
ThisBuild / assembly / assemblyJarName := s"${name.value}-${scalaBinaryVersion.value}-${version.value}.jar"
ThisBuild / assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
