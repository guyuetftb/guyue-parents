package com.gy.classtrait

/**
  * Created by lipeng on 1/12/18.
  */

class NoCaseClass private(age: Int, name: String, address: String) {
  def this(age: Int) = {
    this(age, null, null)
  }
}

class PriClass private() {

}



case class CaseClass(age: Int, name: String){
}

class CaseClassTest() {
  def a(): Unit = {

  }

  def apply():Unit = {
    println(" I am apply")
  }
}

object CaseClassTest {

  def b(): Unit = {
    println("aa")
  }

  def main(args: Array[String]): Unit = {
    val caseOne = CaseClass(1, "lipeng")
    val caseTwo = CaseClass(1, "lipeng")
    println(caseOne == caseTwo)

    val caseO = new CaseClassTest()
    caseO()


    val normalOne = new NoCaseClass(2)

    val normalTwo = new NoCaseClass(2)
    println(normalOne)
    println(normalTwo)
    println(normalOne.eq(normalTwo))
  }


}

