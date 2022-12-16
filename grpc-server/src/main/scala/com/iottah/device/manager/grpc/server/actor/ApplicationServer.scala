package com.iottah.device.manager.grpc.server.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.http.scaladsl.server.Route

object ApplicationServer {
  def apply(httpServerRef: ActorRef[HttpServer.Input]): Behavior[Input] = {
    Behaviors.setup { context =>
      val httpServerOutputMessageAdapter = context.messageAdapter(WrappedHttpServerOutput)

      Behaviors.receiveMessage {
        case Launch(host, port, route) =>
          httpServerRef.tell(HttpServer.Bind(host, port, route, httpServerOutputMessageAdapter))

          Behaviors.same
        case WrappedHttpServerOutput(HttpServer.FailedBinding) =>
          Behaviors.stopped
        case WrappedHttpServerOutput(HttpServer.SuccessfulBinding) =>
          Behaviors.ignore
      }
    }
  }

  sealed trait Input

  final case class Launch(host: String,
                          port: Int,
                          route: Route) extends Input

  private[actor] final case class WrappedHttpServerOutput(output: HttpServer.Output) extends Input
}
