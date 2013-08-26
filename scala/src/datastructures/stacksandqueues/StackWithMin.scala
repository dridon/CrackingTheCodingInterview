package datastructures.stacksandqueues

import scala.collection.mutable.Stack
/** Question 3.2
 *
 * A stack that keeps track of the minimum value, accessible in O(1) time
 * shorts, ints, longs, floats and doubles are compared through their primitive values
 * all other types need a comparator
 *
 * User: faiz
 * Date: 25/08/13
 * Time: 4:49 PM
 * @param comparator an optional comparator that is needed to ensure that a min value is kept
 */
class StackWithMin[T](val comparator : Option[(T, T) => Int] = None) {

  // a stack to keep track of the minimum values
  private val minStack = new scala.collection.mutable.Stack[T]

  // the main stack
  private val stack = new scala.collection.mutable.Stack[T]

  /**
   * Pushes a value on to the stack
   * @param value the value to push on to the stack
   */
  def push(value: T): Unit  = {
    if(pushableToMin(value)) minStack push value
    stack push value
  }

  /**
   * Pops a value from the stack
   * @return Some(T) if the stack isn't Empty, None otherwise
   */
  def pop(): Option[T] = {
    if(minStack.isEmpty && stack.isEmpty) None
    else{
      if (minStack.isEmpty && stack.isEmpty) throw new Exception("Can not have only one stack in min and main stack be empty")
      else {
        if(stack.top == minStack.top) minStack.pop()
        Some(stack.pop())
      }
    }
  }

  /**
   * Returns the min value in the current stack
   * @return returns Some(T) if the stack isn't Empty else None
   */
  def min():Option[T] = if (minStack.isEmpty) None else Some(minStack.top)

  private def defaultComparator(t1 :T) = {

    // for value types
    def valComparator[S](t1: S, t2: S) = (t1, t2) match {
      case (x: Short, y: Short) => if (x<y) -1 else if (x == y) 0 else 1
      case (x: Int, y: Int) => if (x<y) -1 else if (x == y) 0 else 1
      case (x: Long, y: Long) => if (x<y) -1 else if (x == y) 0 else 1
      case (x: Float, y: Float) => if (x<y) -1 else if (x == y) 0 else 1
      case (x: Double, y: Double) => if (x<y) -1 else if (x == y) 0 else 1
      case _ => throw new Exception ("Unknown value type or reference type found")
    }

    // ensure value types are accounted for and return 1 for anything else
    t1 match {
      case x: Short  => if(minStack.isEmpty) -1 else valComparator(x, minStack.top)
      case x: Int  => if(minStack.isEmpty) -1 else valComparator(x, minStack.top)
      case x: Long  => if(minStack.isEmpty) -1 else valComparator(x, minStack.top)
      case x: Float  => if(minStack.isEmpty) -1 else valComparator(x, minStack.top)
      case x: Double  => if(minStack.isEmpty) -1 else valComparator(x, minStack.top)
      case _ => 1
    }
  }
  // a check to ensure ordering, uses a default comparator if none is provided
  private def pushableToMin(value: T): Boolean = comparator match {
    case Some(f) => f(value, minStack.top) <= 0
    case None =>  defaultComparator(value)  <= 0
  }
}
