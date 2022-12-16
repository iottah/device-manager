package com.iottah.device.manager.grpc.server.routing

import akka.actor.typed.ActorSystem
import akka.grpc.scaladsl.{ServerReflection, ServiceHandler}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.iottah.device.manager.grpc.server.service.DeviceManagerV1ServiceImpl
import com.iottah.device.manager.v1.{DeviceManagerService => DeviceManagerV1Service, DeviceManagerServiceHandler => DeviceManagerV1ServiceHandler}

final class DeviceManagerRouteProvider()
                                      (implicit val actorSystem: ActorSystem[_])
  extends RouteProvider {

  override def provide(): Route = {
    handle {
      ServiceHandler.concat(
        ServerReflection.partial(
          List(
            DeviceManagerV1Service
          )
        ),
        DeviceManagerV1ServiceHandler.partial(
          new DeviceManagerV1ServiceImpl()
        )
      )
    } ~ handle {
      ServiceHandler.concatOrNotFound()
    }
  }
}
