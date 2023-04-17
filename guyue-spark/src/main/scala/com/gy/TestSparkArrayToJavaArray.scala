package com.gy

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

;

/**
  * Created by admin on 2018/4/24.
  */
object TestSparkArrayToJavaArray {

  Logger.getLogger("org").setLevel(Level.WARN)

  def main(args: Array[String]): Unit = {

    // import collection.immutable._

    val scalaArr: Array[String] = Array("a", "b", "c")
    TestJava.aaa(scalaArr: _*)

  }
}
