package datastructures.test

import org.scalatest.FunSuite
import datastructures.stacksandqueues._


/** Test class for Stacks and Queues challenges in datastructures.stacksandqueues */
class StacksAndQueuesTest extends FunSuite{

  /** test to see if three stack queue pushes and pops values properly*/
  test("Three stack queue pushes and pops values properly"){
    val stackSet = new ThreeStacks[Int, String, Double](9)

    // in the beginning all stacks should return a none value
    assert(stackSet.pop1() == None)
    assert(stackSet.pop1() == None)
    assert(stackSet.pop1() == None)

    // load all the stacks with test values
    stackSet push1 1
    stackSet push1 2
    stackSet push1 3

    stackSet push2 "1"
    stackSet push2 "2"
    stackSet push2 "3"

    stackSet push3 1.0
    stackSet push3 2.0
    stackSet push3 3.0

    // test if the stack obeys the 9 element limit
    assert(
      try{
        stackSet push1 1
        false
      }
      catch
      {
        case _:Throwable => true
      }
    )

    // test the first pop
    assert(stackSet.pop1() == Some(3))
    assert(stackSet.pop2() == Some("3"))
    assert(stackSet.pop3() == Some(3.0))

    // second pop
    assert(stackSet.pop1() == Some(2))
    assert(stackSet.pop2() == Some("2"))
    assert(stackSet.pop3() == Some(2.0))

    // third pop
    assert(stackSet.pop1() == Some(1))
    assert(stackSet.pop2() == Some("1"))
    assert(stackSet.pop3() == Some(1.0))

    // ensure the stacks are empty
    assert(stackSet.pop1() == None)
    assert(stackSet.pop2() == None)
    assert(stackSet.pop3() == None)

  }

  /**tests to see if the stack return the proper minimum values */
  test("The proper minimum values are returned"){
    val minStack = new StackWithMin[Int]()

    // simple case
    minStack push 4
    minStack push 5

    assert(minStack.min == Some(4) )

    // new min
    minStack push 2
    assert(minStack.min == Some(2))

    // a few more then another new min
    minStack push 6
    minStack push 7
    minStack push 1
    assert(minStack.min == Some(1))

    // test old min
    minStack.pop()
    assert(minStack.min == Some(2))

    // test oldest min
    minStack.pop()
    minStack.pop()
    minStack.pop()

    assert(minStack.min == Some(4))
  }

  /** tests if set of stacks is working properly */
  test("Set of stacks pushes and pops on sets properly"){
    // stacks with threshold of 2
    val setOfStacks = new SetOfStacks[Int](2)

    // first stack
    setOfStacks push 1
    setOfStacks push 2

    // second stack
    setOfStacks push 3
    setOfStacks push 4

    // third stack
    setOfStacks push 5
    setOfStacks push 6

    // test tail pop
    assert(setOfStacks.pop() == Some(6))
    assert(setOfStacks.pop() == Some(5))

    // test popAt
    assert(setOfStacks.popAt(2) == None)
    assert(setOfStacks.popAt(3) == None)
    assert(setOfStacks.popAt(0) == Some(2))
    assert(setOfStacks.pop() == Some(4))
    assert(setOfStacks.pop() == Some(3))
    assert(setOfStacks.pop() == Some(1))
    assert(setOfStacks.pop() == None)

  }

  /** tests to see if the towers of hanoi problem is solved */
  test("Towers of Hanoi solved properly"){
    // towers
    val source = new Tower("A")
    val destination = new Tower("B")
    val spare = new Tower("C")

    // solver
    val towersOfHanoi = new TowersOfHanoi(0)

    // populate and solve
    source.populate(5)
    towersOfHanoi.moveDiscs(5, source, destination, spare)

    // check to see if destination has the right order of discs
    assert(destination.removeDisc() == Some(Disc(0)))
    assert(destination.removeDisc() == Some(Disc(1)))
    assert(destination.removeDisc() == Some(Disc(2)))
    assert(destination.removeDisc() == Some(Disc(3)))
    assert(destination.removeDisc() == Some(Disc(4)))

    // total moves for 5 discs should be 31
    assert(towersOfHanoi.totalMovesUsed() == 31)
  }

  /** tests if MyQueue uses two stacks to generate proper queue behaviour*/
  test("MyQueue behaves properly"){
    val q = new MyQueue[Int]()

    // load elements
    q enqueue 1
    q enqueue 2
    q enqueue 3

    // test to see if they are returned in the right order
    assert(q.dequeue() == Some(1))
    assert(q.dequeue() == Some(2))
    assert(q.dequeue() == Some(3))
  }

  /** tests if the stack is sorted */
  test("Stack sorted properly"){
    val s = scala.collection.mutable.Stack(7,8,4,6,2,8,1,3)
    val sorter = new StackSorter

    sorter.sort(s)

    assert(s.pop() == 8)
    assert(s.pop() == 8)
    assert(s.pop() == 7)
    assert(s.pop() == 6)
    assert(s.pop() == 4)
    assert(s.pop() == 3)
    assert(s.pop() == 2)
    assert(s.pop() == 1)
  }
}
