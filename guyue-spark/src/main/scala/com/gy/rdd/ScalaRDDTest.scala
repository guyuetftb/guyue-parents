package com.gy.rdd

import java.util
import java.util.Random

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import scala.collection.mutable

object ScalaRDDTest {
  def main(args: Array[String]): Unit = {

    //    val file = "";
//
//    val sparkConf = new SparkConf()
//    sparkConf.setAppName("ScalaRDDTest")
//    sparkConf.setMaster("local[*]")
//    val sc = new SparkContext(sparkConf)
//
//    var rdd1 = sc.makeRDD(Seq(("A", 1), ("A", 2), ("A", 3), ("B", 2), ("B", 5), ("B", 2), ("C", 3)), 2)
//    rdd1.mapPartitionsWithIndex{
//      (x,iter) => {
//        var result = List[(String,Int)]()
//        var i = 0
//        while(iter.hasNext){
//          i += iter.next()._2
//        }
//        result.::(x + "|" + i).iterator
//      }
//    }
//    val ssc = new StreamingContext(sparkConf,Seconds(1))
//    ssc
val random = new Random()

    val map = new scala.collection.mutable.HashMap[Int, Int]()
    for (i <- 1 to 10000) {
      val a = Math.floor(Math.random() * 67).toInt + 1
//      val a = random.nextInt(67) + 1
      if (map.contains(a)) {
        map(a) = map.get(a).get + 1
      } else {
        map(a) = 1
      }
    }

    map.toSeq.sortBy(_._1).foreach(x => {
      val partNum = nonNegativeMod(x._1.hashCode(), 67)
      println(x._1 + "   =>   " + x._2 + "   -->   " + partNum)
    })


    //rdd1.to
  }

  def nonNegativeMod(x: Int, mod: Int): Int = {
    val rawMod = x % mod
    rawMod + (if (rawMod < 0) mod else 0)
  }

}
