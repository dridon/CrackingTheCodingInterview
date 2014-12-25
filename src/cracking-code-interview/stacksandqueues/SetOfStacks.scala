package datastructures.stacksandqueues

import scala.annotation.tailrec

/** Question 3.3
  *
  * A stack abstraction over a set of stacks. After a threshold is reached a new
  * stack is used. A specific stack may be popped using popAt()
  * User: faiz
  * Date: 25/08/13
  * Time: 9:27 PM
  *
  * @param stackThreshold the threshold for each stack following which a new stack is used
  * @param stackBuffer the number of empty stacks kept as a buffer to preserve memory
  */
class SetOfStacks[T](stackThreshold: Int = 1000, stackBuffer: Int = 5) {

  // short form, prevents excessive fully qualified names
  private type MutableStack[A] = scala.collection.mutable.Stack[A]

  // A store of stacks that are randomly accessible in O(1) time
  private var stacks = Vector[MutableStack[T]]()

  // the maximum number of values that may be inside any stack
  private val threshold = stackThreshold

  // initially stacks is empty so there is no tail
  private var tail = -1

  // the number of stacks kept to prevent constant instantiation
  private val buffer = stackBuffer

  /**
   * pushes a value on to the last stack
   * @param value the value to be pushed on to the set of stacks stack
   */
  def push(value: T): Unit = {
    // append another stack to stacks
    def extendStacks(): Unit = {
      if (tail >= stacks.length -1 ) stacks = stacks :+ new MutableStack[T]
      tail += 1
    }

    // checks to see if there is any room available on the current stacks tail
    def isTailFull: Boolean= stacks(tail).size >= threshold

    if(stacks.isEmpty || isTailFull) extendStacks()

    stacks(tail) push value
  }

  /**
   * pops a value off the first available stack
   * @return Some(value) at the tail of stacks, None if tails are empty
   */
  def pop():Option[T] = {

    @tailrec def nonEmptyStackIndex(index: Int):Int = {
      if (index <= 0) 0
      else if(stacks(index).length > 0) index
      else nonEmptyStackIndex(index -1 )
    }

    // clears stacks if more than 5 empty stacks are at the end
    def trimStacks(): Unit = stacks =  {
      val dropCount = stacks.length - tail - buffer
      stacks.dropRight(if (dropCount > 0 ) dropCount else 0)
    }

    // no more items left
    if(tail == 0 && stacks(0).isEmpty) None

    // pop off the last non empty stack
    else  { trimStacks();  tail = nonEmptyStackIndex(tail); Some(stacks(tail).pop()) }
  }

  /**
   * Pops a value off the stack at position index. If the stack is
   * empty at that position or if the position doesn't exist then
   * None is returned
   * @param index an index representing the stack to be accessed
   * @return Some(value) if it exists in the stack at that position, None otherwise
   */
  def popAt(index: Int): Option[T] = {
    // assertions are stronger in popAt when compared to pop
    if(index >= stacks.length || index < 0 || stacks(index).length == 0) None
    else{
      // save old tail
      val tailSave = tail
      tail = index

      // let pop do the work and restore tail then return
      val value = pop()
      tail = tailSave
      value
    }
  }


}
