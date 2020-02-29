package com.gy.streaming

import java.util

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingHDFSWordCount {

  def main(args: Array[String]): Unit = {
    val master = "local[2]"
    val appName = "HdfsWordCount"
    val file = "file:///Users/lipeng/workspace_guyue/practice_data/streaming"
    val conf = new SparkConf().setMaster(master).setAppName(appName)
    val ssc = new StreamingContext(conf, Seconds(50))

    val lines = ssc.textFileStream(file)
    val words = lines.flatMap(line => line.split(" "))
    val wordsCount = words.map((_, 1)).reduceByKey(_ + _)
    wordsCount.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
