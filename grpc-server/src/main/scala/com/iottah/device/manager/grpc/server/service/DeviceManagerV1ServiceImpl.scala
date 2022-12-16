package com.iottah.device.manager.grpc.server.service

import com.iottah.device.manager.v1.{CreateRequest, CreateResponse, DeviceManagerService}

import scala.concurrent.Future

final class DeviceManagerV1ServiceImpl() extends DeviceManagerService {
  override def create(in: CreateRequest): Future[CreateResponse] =
    Future.successful(CreateResponse.of(in.name))
}
