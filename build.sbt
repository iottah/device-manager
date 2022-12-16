name := "device-manager"
publish / skip := true

Global / excludeLintKeys ++= Set(versions)

lazy val root = Project("device-manager", file("."))
  .settings(Common.settings)
  .aggregate(
    domain,
    grpcServer
  )

lazy val domain = project.in(file("domain"))
  .settings(Common.settings)

lazy val infrastructure = project.in(file("infrastructure"))
  .settings(Common.settings)
  .dependsOn(domain)

lazy val grpcServer = project.in(file("grpc-server"))
  .settings(Common.settings)
  .dependsOn(infrastructure)
  .enablePlugins(AkkaGrpcPlugin, JavaServerAppPackaging)
