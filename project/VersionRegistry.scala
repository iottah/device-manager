sealed trait VersionRegistry {
  final lazy val scala = "2.13.10"
  final lazy val akka = "2.7.0"
  final lazy val akkaHttp = "10.4.0"
  final lazy val akkaManagement = "1.2.0"
  final lazy val akkaProjection = "1.3.1"
  final lazy val logback = "1.4.5"
  final lazy val h2 = "2.1.214"
  final lazy val postgresql = "42.5.1"
  final lazy val alpakka = "5.0.0"
  final lazy val scalatest = "3.2.14"
  final lazy val scalamock = "5.2.0"
  final lazy val flyway = "9.10.0"
}

object VersionRegistry extends VersionRegistry
