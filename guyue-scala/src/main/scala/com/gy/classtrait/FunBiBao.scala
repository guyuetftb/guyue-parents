package com.gy.classtrait

/**
  * Created by lipeng on 1/12/18.
  */
object FunBiBao {

  // mulBy函数接收一个参数,并返回一函数,每个函数体内的factor的值都是不一样的。
  def mulBy(factor: Int) = {
    (x: Int) => {
      factor * x;
    }
  }

  def m1(mul: Int => Int) = {
    (1 to 10) map mul
  }


  def main(args: Array[String]) {

    val funVal = m1 _


    println(m1(mulBy(3))); // factor=3
    println(m1(mulBy(5))); // factor = 0.5
  }
}
