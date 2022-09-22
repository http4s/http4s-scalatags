ThisBuild / tlBaseVersion := "1.0"
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

val Scala213 = "2.13.9"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.1.2")
ThisBuild / scalaVersion := Scala213

lazy val root = project.in(file(".")).aggregate(scalatags).enablePlugins(NoPublishPlugin)

val http4sVersion = "1.0.0-M35"
val scalatagsVersion = "0.11.1"
val munitVersion = "0.7.29"
val munitCatsEffectVersion = "1.0.7"

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
      "org.typelevel" %%% "munit-cats-effect-3" % munitCatsEffectVersion % Test,
      "org.http4s" %%% "http4s-laws" % http4sVersion % Test,
    ),
  )
