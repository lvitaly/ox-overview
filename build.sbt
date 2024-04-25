val scala3Version = "3.3.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "ox-overview",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    javacOptions ++= Seq("--release", "21", "--enable-preview"),

    javaOptions += "--enable-preview",

    libraryDependencies ++= Seq(
      "com.softwaremill.ox" %% "core" % "0.1.0",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
