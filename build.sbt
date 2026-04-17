ThisBuild / tlBaseVersion := "0.25"
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

val Scala213 = "2.13.18"
ThisBuild / crossScalaVersions := Seq("2.12.21", Scala213, "3.3.7")
ThisBuild / scalaVersion := Scala213
ThisBuild / startYear := Some(2018)

lazy val root = tlCrossRootProject.aggregate(scalatags)

val http4sVersion = "0.23.34"
val scalatagsVersion = "0.13.1"
val munitVersion = "1.3.0"
val munitCatsEffectVersion = "2.2.0"

lazy val scalatags = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("scalatags"))
  .settings(
    name := "http4s-scalatags",
    description := "Scalatags template support for http4s",
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-core" % http4sVersion,
      "com.lihaoyi" %%% "scalatags" % scalatagsVersion,
      "org.scalameta" %%% "munit-scalacheck" % munitVersion % Test,
      "org.typelevel" %%% "munit-cats-effect" % munitCatsEffectVersion % Test,
      "org.http4s" %%% "http4s-laws" % http4sVersion % Test,
    ),
  )
  .nativeSettings(
    unusedCompileDependenciesTest := {},
    tlVersionIntroduced := List("2.12", "2.13", "3").map(_ -> "0.25.2").toMap

  )
