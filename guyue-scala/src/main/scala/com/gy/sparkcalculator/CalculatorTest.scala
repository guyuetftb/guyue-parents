package com.gy.sparkcalculator

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

abstract class SuperTeacher(name: String) {
  var id: Int;
  var age: Int;
  def teach: Unit;
}

class TeacherForMaths(name: String) extends SuperTeacher(name) {
  override var id = name.hashCode;
  override var age = 30;
  override def teach(): Unit = {
    println(" Teaching Maths!");
  }
}

object AbstractClassOps {
  def main(args: Array[String]) {
    val teacher = new TeacherForMaths("Spark");
    teacher.teach();
    println(" id = " + teacher.id);
    println(" age = " + teacher.age);
    //println(" name = " + teacher.name);// 报错，因为父类中没有方法使该字段。
  }
}
