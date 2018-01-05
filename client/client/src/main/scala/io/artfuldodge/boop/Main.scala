package io.artfuldodge.boop

import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {
  val channel =
    io.grpc.ManagedChannelBuilder.forTarget("localhost:9990")
      .usePlaintext(true)
      .build
  val client = new BoopGrpc.BoopStub(channel)
  val resp = Await.result(client.boop(BoopRequest("snaek")), 10.seconds)
  println(resp)
}
