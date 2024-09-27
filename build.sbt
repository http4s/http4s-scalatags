ThisBuild / tlBaseVersion := "1.0"
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

val Scala213 = "2.13.10"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.3.4")

lazy val root = tlCrossRootProject.aggregate(scalatags)

val http4sVersion = "1.0.0-M39"
val scalatagsVersion = "0.12.0"
val munitVersion = "1.0.0-M7"
val munitCatsEffectVersion = "2.0.0-M3"

lazy val scalatags = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
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
  .nativeSettings(
    unusedCompileDependenciesTest := {}
  )
