package com.gy.classtrait

/**
  * Created by lipeng on 1/10/18.
  *
  *
  * class Worker private声明了Worker的首构造函数是私有的，
  * 这样Worker的所有构造函数都不能直接被外部调用，因为所有从构造函数都会首先调用其他构造函数
  * （可以是主构造函数，也可以是从构造函数），结果就是主构造函数是类的唯一入口点。
  *
  */
class Teacher {
  def work() = println("I am the only worker!")
}

object Teacher {

  def GetWorkInstance(): Teacher = {
    null
  }
}

object Job {
  def main(args: Array[String]) {
    for (i <- 1 to 5) {
      Teacher.GetWorkInstance();
    }
  }
}

