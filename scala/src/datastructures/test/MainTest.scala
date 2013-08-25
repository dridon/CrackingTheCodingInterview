package datastructures.test

object MainTest {

  def main(args: Array[String]){

    def printHorizontalLine():Unit = {
      println("\n--------------------------------\n")
    }
    //initialize all tests 
    val arrayAndStringTest = new ArraysAndStringsTest
    val linkedListTest = new LinkedListsTest
    val stacksandQueuesTest = new StacksAndQueuesTest
    
    // execute all tests
    arrayAndStringTest.execute()
    printHorizontalLine()

    linkedListTest.execute()
    printHorizontalLine()

    stacksandQueuesTest.execute()
    printHorizontalLine()
  }

}