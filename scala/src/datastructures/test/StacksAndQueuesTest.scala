package datastructures.test

import org.scalatest.FunSuite
import datastructures.stacksandqueues._


/** Test class for Stacks and Queues challenges in datastructures.stacksandqueues */
class StacksAndQueuesTest extends FunSuite{

  /** test to see if three stack queue pushes and pops values properly*/
  test("Three stack queue pushes and pops values properly"){
    val stackSet = new ThreeStacks[Int, String, Double](10)

    // in the beginning all stacks should return a none value
    assert(stackSet.stack1.pop() == None)
    assert(stackSet.stack2.pop() == None)
    assert(stackSet.stack3.pop() == None)

    // load all the stacks with test values
    stackSet.stack1.push(1)
    stackSet.stack1.push(2)
    stackSet.stack1.push(3)

    stackSet.stack2.push("1")
    stackSet.stack2.push("2")
    stackSet.stack2.push("3")

    stackSet.stack3.push(1.0)
    stackSet.stack3.push(2.0)
    stackSet.stack3.push(3.0)


    // test the first pop
    assert(stackSet.stack1.pop() == Some(3))
    assert(stackSet.stack2.pop() == Some("3"))
    assert(stackSet.stack3.pop() == Some(3.0))

    // second pop
    assert(stackSet.stack1.pop() == Some(2))
    assert(stackSet.stack2.pop() == Some("2"))
    assert(stackSet.stack3.pop() == Some(2.0))

    // third pop
    assert(stackSet.stack1.pop() == Some(1))
    assert(stackSet.stack2.pop() == Some("1"))
    assert(stackSet.stack3.pop() == Some(1.0))

    // ensure the stacks are empty
    assert(stackSet.stack1.pop() == None)
    assert(stackSet.stack2.pop() == None)
    assert(stackSet.stack3.pop() == None)
  }

}
