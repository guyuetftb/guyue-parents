package com.gy.scalabasic

/**
  * Created by lipeng on 5/15/17.
  */
object ScalaOperator {

  def main(args: Array[String]) {
    println("------- Tuple [init] ")
    val tupA = ("a", 1)
    val tupB = ("b" -> 2)
    val tupC = "c" -> 3
    val tupD = new Tuple3[String, String, String]("a", "b", "c")
    println(" tupD._3 = " + tupD._3)

    val (first, second ,_) = tupD
    println(" first = " + first)
    println(" second = " + second)

    val keys = Array("a","b","c")
    val values = Array(1,2,3)
    val pairs = keys.zip(values)
    println(" pairs = " + pairs.mkString("\t"))

    val map = pairs.toMap
    println(" is Map = " + map.isInstanceOf[scala.collection.immutable.Map[String,Int]])

  }
}
