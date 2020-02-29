package com.gy.akka

import akka.actor.Actor

/**
  * Created by lipeng on 2018/1/24.
  */

object Greeter {

  case object Greet

  case object Done

}

class Greeter extends Actor {
  def receive = {
    case Greeter.Greet =>
      println("Hello World! I am Greeter, I receive the message. ")
      sender() ! Greeter.Done
  }
}
