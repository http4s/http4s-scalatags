ThisBuild / tlBaseVersion := "0.25"
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

val Scala213 = "2.13.9"
ThisBuild / crossScalaVersions := Seq("2.12.17", Scala213, "3.1.3")
ThisBuild / scalaVersion := Scala213

lazy val root = project.in(file(".")).aggregate(scalatags).enablePlugins(NoPublishPlugin)

val http4sVersion = "0.23.16"
val scalatagsVersion = "0.12.0"
val munitVersion = "1.0.0-M6"
val munitCatsEffectVersion = "2.0.0-M3"

lazy val scalatags = project
  .in(file("scalatags"))
  .settings(
    name := "http4s-scalatags",
    description := "Scalatags template support for http4s",
    startYear := Some(2018),
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-core" % http4sVersion,
      "com.lihaoyi" %%% "scalatags" % scalatagsVersion,
      "org.scalameta" %%% "munit-scalacheck" % munitVersion % Test,
      "org.typelevel" %%% "munit-cats-effect" % munitCatsEffectVersion % Test,
      "org.http4s" %%% "http4s-laws" % http4sVersion % Test,
    ),
  )
