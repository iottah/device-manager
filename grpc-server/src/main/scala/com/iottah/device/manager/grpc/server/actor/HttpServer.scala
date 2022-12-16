package com.iottah.device.manager.grpc.server.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Sink
import com.iottah.device.manager.grpc.server.middleware.HttpBindingProvider

import scala.util.{Failure, Success}

object HttpServer {
  def apply(httpBindingProvider: HttpBindingProvider): Behavior[Input] =
    Behaviors.setup { context =>
      import context.system

      Behaviors.receiveMessage {
        case Bind(host, port, route, replyTo) =>
          context.pipeToSelf(httpBindingProvider.provide(host, port, route).runWith(Sink.head)) {
            case Failure(exception) =>
              LogBindingFailure(exception, replyTo)
            case Success(binding) =>
              LogBindingSuccess(binding, replyTo)
          }

          Behaviors.same
        case LogBindingFailure(exception, replyTo) =>
          context.log.error("Failed to bind HTTP endpoint!", exception)

          replyTo.tell(FailedBinding)

          Behaviors.same
        case LogBindingSuccess(binding, replyTo) =>
          context.log.info("Server online at http://{}:{}/", binding.localAddress.getHostString, binding.localAddress.getPort)

          replyTo.tell(SuccessfulBinding)

          Behaviors.same
      }
    }

  sealed trait Input

  sealed trait Output

  final case class Bind(host: String,
                        port: Int,
                        route: Route,
                        replyTo: ActorRef[Output]) extends Input

  case object SuccessfulBinding extends Output

  case object FailedBinding extends Output

  private[this] final case class LogBindingFailure(exception: Throwable,
                                                   replyTo: ActorRef[Output]) extends Input

  private[this] final case class LogBindingSuccess(binding: Http.ServerBinding,
                                                   replyTo: ActorRef[Output]) extends Input

}
