package com.iottah.device.manager.grpc.server

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorSystem, Behavior, Terminated}
import akka.http.scaladsl.server.Directives.concat
import com.iottah.device.manager.grpc.server.actor.{ApplicationServer, HttpServer}
import com.iottah.device.manager.grpc.server.middleware.DefaultHttpBindingProvider
import com.iottah.device.manager.grpc.server.routing.DeviceManagerRouteProvider
import com.typesafe.config.ConfigFactory

import java.io.File

final class ServerApplication(context: ActorContext[_]) {
  import context.system

  private def behavior(): Behavior[Nothing] = {
    val httpServerRef = context.spawnAnonymous(HttpServer(new DefaultHttpBindingProvider()))

    val applicationServerRef = context.spawnAnonymous(ApplicationServer(
      httpServerRef
    ))

    val routes = Seq(
      new DeviceManagerRouteProvider()
        .provide()
    )

    applicationServerRef.tell(ApplicationServer.Launch("0.0.0.0", -1, concat(routes: _*)))

    context.watch(applicationServerRef)

    Behaviors.receiveSignal[Nothing] {
      case (_, Terminated(_)) =>
        Behaviors.stopped
    }
  }
}

object ServerApplication {
  private final val ConfigPath = "CONFIG_PATH"

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.parseFileAnySyntax(new File(sys.env(ConfigPath)))
      .withFallback(ConfigFactory.load())
      .resolve()

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      new ServerApplication(context)
        .behavior()
    }

    ActorSystem[Nothing](rootBehavior, "ServerApplication", config)
  }
}
