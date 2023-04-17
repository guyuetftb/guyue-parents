package com.gy.classtrait

/**
  * Created by lipeng on 2018/1/19.
  */
class TeacherConstructor(name:String,age:Int) {


  println("每次实例化对象时都会打印此句.");

  // 报错
  // 因为主构造函数中已经了相同名称的变量、常量。
  // 去掉主构造函数中变量前的val、var将不会有问题。
  var name :String = _; // 对变量赋初始值null
  private var age:Int = _; //赋初始值0
  private[this] val gender = "male";

  // 副构造函数 定义 必须在方法前面 加def关键字.
  // 副构造函数 定义 参数列表后的 =等号, 可有，可无
  def this(nameTmp:String){
    // 1. 必须调用其他构造器，不用非得是主构造器，但最终会调用主构造器。
    // 2. 必须放在第一句。
    this(nameTmp,-1)
    //this.name = nameTmp
  }
}

object TeacherConstructor{
  def main(args: Array[String]): Unit = {
    val teacherConstructor = new TeacherConstructor("li",1)
    val t2 = new TeacherConstructor("yue")
    println(teacherConstructor.name)
    println(teacherConstructor.age)
    println(t2.name)
    println(t2.age)
  }
}
