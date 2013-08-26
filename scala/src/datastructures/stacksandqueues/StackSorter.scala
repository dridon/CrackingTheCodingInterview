package datastructures.stacksandqueues

import scala.annotation.tailrec
/** Question 3.6
 *
 * A sorter for integer stacks that runs in O(n &#94; 2) time
 *
 * User: faiz
 * Date: 26/08/13
 * Time: 5:41 PM
 */
class StackSorter{
  type MutableStack[A] =  scala.collection.mutable.Stack[A]
  val MutableStack = scala.collection.mutable.Stack
  /**
   * Sorts a stack of integers
   * @param s sorts stack of size
   */
  def sort(s: scala.collection.mutable.Stack[Int]): Unit = {
    // helps to sort the stack
    val helper = new MutableStack[Int]

    // moves all elements from helper to stack if they are bigger than the reference
    @tailrec def takeFromHelper(reference: Int, stack: MutableStack[Int], helper: MutableStack[Int]): Unit = {
      if(!helper.isEmpty && helper.top > reference) {
        stack push helper.pop()
        takeFromHelper(reference, stack, helper)
      }
    }

    // moves all elements from stack to helper in a sorted fashion
    @tailrec def sortStackHelper(stack: MutableStack[Int], helper: MutableStack[Int]): Unit = {
      if(!stack.isEmpty){
        val temp = stack.pop()
        takeFromHelper(temp, stack, helper)
        helper push temp
        sortStackHelper(stack, helper)
      }
    }

    // sorts s
    def sortStack(s: MutableStack[Int], helper: MutableStack[Int]): Unit = {
      sortStackHelper(s, helper)
      val sortedStack = MutableStack[Int]().pushAll(helper)
      s.pushAll(sortedStack)
    }

    // sort s
    sortStack(s, helper)
  }

}
