package com.gy.interview.jd

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 2018/1/30.
  * 2018-1-29 去京东面试, 有一个文件,内容是这样的：
  * 1,2,3,4
  * 2,4,5,6
  *
  * 要求,统计第2例的最大值, 同时计算 第4列的总各。
  * 我当时，注是按照这个方式写的.
  * 很明显,我写对了，但是，不明白，为什么没有到二面.
  *
  */
object JDSparkCount {

  val file = "/Users/lipeng/workspace_engine/guyue-spark/src/main/scala/com/gy/interview/jd/test.txt";

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
    sparkConf.setAppName("test-")
    sparkConf.setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    val initRdd = sc.textFile(file)
    initRdd.map(line => {
      val arr = line.split(",")
      (arr(1).toLong, arr(3).toLong)
    }).aggregate(0l, 0l)(
      (acc, num) => ((math.max(acc._1, num._1), acc._2 + num._2)),
      (acc1, acc2) => ((math.max(acc1._1, acc2._1), acc1._2 + acc2._2))
    )
  }
}
