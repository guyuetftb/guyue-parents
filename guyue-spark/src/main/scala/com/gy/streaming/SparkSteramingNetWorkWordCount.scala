package com.gy.streaming

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkSteramingNetWorkWordCount {
  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "NetWorkWordCount"
    val host = "127.0.0.1"
    val port = "9999"
    val conf = new SparkConf().setMaster(master).setAppName(appName)

    val ssc = new StreamingContext(conf, Seconds(10))

    val lines = ssc.socketTextStream(host, port.toInt)
    val words = lines.flatMap(line => line.split(" "))
    val wordCounts = words.map((_, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
