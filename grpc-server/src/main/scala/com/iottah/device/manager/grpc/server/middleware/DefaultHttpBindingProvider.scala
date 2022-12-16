package com.iottah.device.manager.grpc.server.middleware

import akka.NotUsed
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Source

final class DefaultHttpBindingProvider()
                                      (implicit actorSystem: ActorSystem[_]) extends HttpBindingProvider {
  override def provide(host: String, port: Int, route: Route): Source[Http.ServerBinding, NotUsed] =
    Source.future(
      Http()
        .newServerAt(host, port)
        .bind(route)
    )
}
