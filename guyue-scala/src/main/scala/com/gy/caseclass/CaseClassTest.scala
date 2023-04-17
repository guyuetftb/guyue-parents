package com.gy.caseclass

import scala.util.Try


class Test(age: Int) {

  def this(age: Int, address: String) = {
    this(age)
  }

  def anotherMthed(x: String): Int = {
    0
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val t = new Test(99, "1")

  }
}


