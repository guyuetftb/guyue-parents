package com.gy.classtrait

/**
  * Created by lipeng on 1/12/18.
  */
abstract class Person(name: String, age: Int) {

  def showName(): Unit = {
    println(" Person name = " + name)
  }

  def display(): Unit

  def noParam: Unit

}

class Worker(name: String, age: Int, address: String) extends Person(name, age) {


  override val noParam = ()

  override def showName(): Unit = {
    println(" Worker name = " + name)
  }

  def display(): Unit = {
    println(" I am implements display method")
  }
}

object Breed extends Enumeration{
  type Breed = Value
  val dane = Value("Great Dane")
}
