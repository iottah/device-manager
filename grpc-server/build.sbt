name := "device-manager-grpc-server"
maintainer := "eduardas.kazakas@nielsen.com"
publish / skip := true

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % versions.value.h2 % Runtime,
  "org.postgresql" % "postgresql" % versions.value.postgresql % Runtime,
  "com.typesafe.akka" %% "akka-http" % versions.value.akkaHttp,
  "ch.qos.logback" % "logback-classic" % versions.value.logback
)

libraryDependencies ++= Seq(
  "com.lightbend.akka.management" %% "akka-management-cluster-http",
  "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap",
  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api"
).map(_ % versions.value.akkaManagement)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % versions.value.scalatest,
  "org.scalamock" %% "scalamock" % versions.value.scalamock,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % versions.value.akka,
  "com.typesafe.akka" %% "akka-http-testkit" % versions.value.akkaHttp
).map(_ % Test)
