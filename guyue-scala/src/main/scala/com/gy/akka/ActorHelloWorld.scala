package com.gy.akka

import akka.actor.{Actor, Props}

/**
  * Created by lipeng on 2018/1/24.
  */
class ActorHelloWorld extends Actor {

  override def preStart(): Unit = {
    // create the greeter actor
    val greeter = context.actorOf(Props[Greeter], "greeter")
    // tell it to perform the greeting
    greeter ! Greeter.Greet
  }

  def receive = {
    // when the greeter is done, stop this actor and with it the application
    case Greeter.Done => context.stop(self)
  }
}
