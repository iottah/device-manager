name := "device-manager-infrastructure"
maintainer := "eduardas.kazakas@nielsen.com"
publish / skip := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster-sharding-typed"
).map(_ % versions.value.akka)
