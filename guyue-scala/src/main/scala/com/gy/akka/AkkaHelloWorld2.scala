package com.gy.akka

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}

/**
  * Created by lipeng on 2018/1/24.
  */
object AkkaHelloWorld2 {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("HelloWorldSystem")
    val a = system.actorOf(Props[ActorHelloWorld], "helloWorld")
    system.actorOf(Props(classOf[Terminator], a), "terminator")
  }

  class Terminator(ref: ActorRef) extends Actor with ActorLogging {
    context watch ref

    def receive = {
      case Terminated(_) =>
        log.info("{} has terminated, shutting down system", ref.path)
        //context.system.terminate()
    }
  }

}
