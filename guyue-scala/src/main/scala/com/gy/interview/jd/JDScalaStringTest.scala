package com.gy.interview.jd

/**
  * Created by lipeng on 2018/1/30.
  * 2018-1-29 去京东面试,要求写一个统计 字符串 中某个 字母包含的个数
  * 当时用的是 spark的思想，写rdd，转换，过滤，
  * 其实最后的结果是 通过 String类的count方法，直接就能统计出字母的个数.
  */
object JDScalaStringTest {
  def main(args: Array[String]): Unit = {
    val str = "abcdeffff"
    val charCount = str.count(_ == 'f')
    println("f count = " + charCount)
  }
}