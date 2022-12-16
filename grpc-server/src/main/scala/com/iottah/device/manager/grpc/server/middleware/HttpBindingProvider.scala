package com.iottah.device.manager.grpc.server.middleware

import akka.NotUsed
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Source

trait HttpBindingProvider {
  def provide(host: String, port: Int, route: Route): Source[Http.ServerBinding, NotUsed]
}
