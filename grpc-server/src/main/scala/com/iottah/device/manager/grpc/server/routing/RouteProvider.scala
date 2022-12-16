package com.iottah.device.manager.grpc.server.routing

import akka.http.scaladsl.server.Route

trait RouteProvider {
  def provide(): Route
}
