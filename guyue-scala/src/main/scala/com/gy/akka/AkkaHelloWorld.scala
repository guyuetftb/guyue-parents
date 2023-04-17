package com.gy.akka

/**
  * Created by lipeng on 2018/1/24.
  */
object AkkaHelloWorld {

  /**
    * Description:
    * <pre>
    * </pre>
    *
    * @param args 主程序入口
    */
  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[ActorHelloWorld].getName))
  }
}
