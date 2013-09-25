package datastructures.stacksandqueues

import scala.annotation.tailrec

/** Question 3.5
 *
 * An implementation of Queue using two underlying stacks
 *
 * User: faiz
 * Date: 26/08/13
 * Time: 5:13 PM
 */
class MyQueue[A] {

  // the two stacks
  private val inbox = new scala.collection.mutable.Stack[A]()
  private val outbox = new scala.collection.mutable.Stack[A]()

  /**
   * adds a value to the queue
   * @param value the value to be added to the queue
   */
  def enqueue(value: A): Unit = {
    inbox.push(value)
  }

  /**
   * removes a value from the queue
   * @return Some(A) if the queue is not empty, None otherwise
   */
  def dequeue(): Option[A] = {
    if(outbox.isEmpty) moveToOutbox()
    if(outbox.isEmpty) None else Some(outbox.pop())
  }

  // moves all elements from inbox to outbox
  @tailrec private def moveToOutbox():Unit = if(!inbox.isEmpty){
    outbox.push(inbox.pop())
    moveToOutbox()
  }
}
