package com.gy.tailrec

import scala.annotation.tailrec

/**
  * Created by lipeng on 1/12/18.
  */
object TailRecTest {
  def main(args: Array[String]): Unit = {

    def factorial(n: Int): Int = {
      @tailrec
      def loop(acc: Int, n: Int): Int =
        if (n == 0) {
          acc
        } else {
          loop(n * acc, n - 1)
        }

      loop(1, n)
    }

    val a = new Array[Int](3)

    val sum = factorial(3)
    println(" sum = " + sum)
  }
}
