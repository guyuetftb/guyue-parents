

/**
  * Created by lipeng on 11/20/17.
  */
object TestScala {

  def main(args: Array[String]): Unit = {
    val list = List(Some("a"), None, Some("b"), None, Some("c"), Some("d"))
    for {Some(ele) <- list
         b = ele.toUpperCase} println(b)
    println(resource)
    println(resource)
    for(a <- MyEnum.values) {
      println(a.id + " , " + a.toString)
    }

    println("-------------")
    checkY(100)

    val nonEmptySeq = Seq(1,2,3,4,5)

    val nonEmptySeq2 = Seq(nonEmptySeq:_*)
    println("11" + nonEmptySeq2)
    println(seqToString(nonEmptySeq))

    val langus = Seq(("one","two"),("1","2"))
    for(t <- langus){
      t match {
        case ("one",_) => println(" Found one")
        case (a,b) => println("a = " + a + ", b = " + b)
      }
    }

    for(i <- Seq(1,2,3,4)){
      i match {
        case _ if i%2 == 0 => println(s"even:$i")
        case _  => println(s"odd:$i")
      }
    }

    val emptySeq = Seq.empty
    emptySeq.head
    emptySeq.tail

  }

  def seqToString[T](seq:Seq[T]):String = seq match {
    case a +: b => s"$a +: " + seqToString(b)
    case Nil => "Nil"
  }

  def checkY(y:Int) = {
    val aa = Seq.empty
    for(b <- Seq(99,100,101)){
      val res = b match {
        case `y` => "found y"
        case i:Int => "int:" + i
      }
      println(res)
    }
  }

  lazy val resource:Int = init()
  def init():Int = {
    println("xxxxxxxxx")
    0
  }


}

object MyEnum extends Enumeration{
  type MyEnum = Value
  val one = Value("one")
  val two = Value(1,"two")
  val three = Value
}

