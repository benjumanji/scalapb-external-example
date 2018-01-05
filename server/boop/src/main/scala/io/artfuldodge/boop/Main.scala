package io.artfuldodge.boop

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.global
import scala.sys

case object Booper extends BoopGrpc.Boop {
  def boop(req: BoopRequest): Future[BoopResponse] = {
    Future.successful(BoopResponse(s"${req.name} boops le snoot"))
  }
}

object Main extends App {
  val server = io.grpc.ServerBuilder.forPort(9990)
    .addService(BoopGrpc.bindService(Booper, global))
    .build
    .start

  sys.addShutdownHook {
    server.shutdownNow
  }

  server.awaitTermination
}
