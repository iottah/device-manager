import lmcoursier.definitions.Authentication
import sbt.Keys._
import sbt._

object Common {
  lazy val settings: Seq[Def.Setting[_]] = Seq(
    organization := "com.iottah",
    scalaVersion := VersionRegistry.scala,
    scalacOptions ++= Seq("-unchecked", "-deprecation"),
    resolvers ++= Seq[sbt.Resolver](
      Resolver.jcenterRepo
    )
  )
}
