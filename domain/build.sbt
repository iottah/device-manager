name := "device-manager-domain"
maintainer := "eduardas.kazakas@nielsen.com"
publish / skip := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence-typed" % versions.value.akka
)
