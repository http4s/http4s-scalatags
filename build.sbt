ThisBuild / tlBaseVersion := "1.0"
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)
ThisBuild / startYear := Some(2018)

val Scala213 = "2.13.18"
val Scala3 = "3.9.0"

ThisBuild / crossScalaVersions := Seq(Scala213, Scala3)
ThisBuild / scalaVersion := Scala213

ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.temurin("17"),
  JavaSpec.temurin("8"),
  JavaSpec.temurin("11"),
  JavaSpec.temurin("21"),
)

ThisBuild / githubWorkflowBuildMatrixExclusions := Seq(
  MatrixExclude(Map("scala" -> "3", "java" -> "temurin@8")),
  MatrixExclude(Map("scala" -> "3", "java" -> "temurin@11")),
  MatrixExclude(Map("scala" -> "2.13", "java" -> "temurin@21")),
  MatrixExclude(
    Map("scala" -> "2.13", "project" -> "rootJS", "java" -> "temurin@11")
  ),
  MatrixExclude(
    Map("scala" -> "2.13", "project" -> "rootNative", "java" -> "temurin@11")
  ),
)

lazy val root = tlCrossRootProject.aggregate(scalatags)
val http4sVersion = "1.0.0-M49"
val scalatagsVersion = "0.13.1"
val munitVersion = "1.3.1"
val munitCatsEffectVersion = "2.2.1"

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
    unusedCompileDependenciesTest := {}
  )
