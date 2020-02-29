package com.gy.streaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 1. 先启动客户端Socket服务
  * > nc -lk 9999
  * 2. 再启动本程序
  * 3. 在客户中随便输入内容
  * 4. 服务端会定期的打印出输入的内容
  */
object SparkStreamingHelloWorld {
  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setMaster("local[2]").setAppName("SparkStreamingHelloWorld")
    val sc = new StreamingContext(conf, Seconds(10))
    val myDStream = sc.socketTextStream("127.0.0.1", 9999)
    myDStream.print()
    sc.start()
    sc.awaitTermination()
  }
}
