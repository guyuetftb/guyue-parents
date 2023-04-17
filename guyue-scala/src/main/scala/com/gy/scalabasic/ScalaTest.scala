package com.gy.scalabasic

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lipeng on 5/15/17.
  */
object Curring {

  def main(args: Array[String]) {
    def multiple(x: Int, y: Int) = {
      x * y
    };

    def multipleOne(x: Int) = {
      println("aaaaaaa")
      (y: Int) => {
        x * y;
      }
    }

    val fun1 = multipleOne(5);
    println(fun1(7));
    //柯里化简写。柯里化最常用的地方,就是参数的推导
    def curring(x: Int)(y: Int) = x * y;

    val fun2 = curring(10)(_: Int); //返回函数
    println(fun2(3));
    println(curring(10)(2)); //直接调用柯里化函数。

    val a = Array("Hello", "Spark");
    val b = Array("Hello", "Spark");
    println(a.corresponds(b)(_.equalsIgnoreCase(_)))


    val sparkConf = new SparkConf().setAppName("xx")
    val sc = new SparkContext(sparkConf)
    val rdd3 = sc.makeRDD(Array(("A", 1), ("A", 2), ("A", 3), ("B", 1), ("B", 2), ("C", 1),
      ("C", 2), ("C", 3), ("C", 4)))
    rdd3.combineByKey(
      (v) => {
        (v, 1)
      },
      (acc: (Int, Int), v) => {
        (acc._1 + v, acc._2 + 1)
      },
      (acc1: (Int, Int), acc2: (Int, Int)) => {
        (acc1._1 + acc2._1, acc1._2 + acc2._2)
      }
    ).map {
      case (key, value) => {
        (key, value._1 + "_" + value._2.toFloat)
      }
    }.collect()


    val data = sc.parallelize(List(("1", "www"), ("1", "iteblog"), ("1", "com"),
      ("2", "bbs"), ("2", "iteblog"), ("2", "com"), ("3", "good")))

    val result = data.combineByKey(
      List(_),
      (x: List[String], y: String) => {
        y :: x
      },
      (x: List[String], y: List[String]) => {
        x ::: y
      })

    val rdd4 = sc.parallelize(List(("A_1_a", "1"), ("A_1_a", "2"), ("A_1_a", "3"), ("B_2_b", "1"), ("B_2_b", "2"), ("B_2_b", "1")))
    rdd4.combineByKey(
      (word: String) => {
        val a = new scala.collection.mutable.HashSet[String]
        a.+=(word)
        a
      },
      (x: scala.collection.mutable.HashSet[String], y: String) => {
        x.+=(y)
      },
      (x: scala.collection.mutable.HashSet[String], y: scala.collection.mutable.HashSet[String]) => {
        x.++=(y)
      })
      .map(x => {
        (x._1, x._2.toList.sortWith(_ > _).take(1))
      })
      .collect()
  }

}
