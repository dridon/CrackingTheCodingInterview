package datastructures

import scala.collection.mutable.{LinkedList => MutableLinkedList}
import scala.annotation.tailrec

/** Container object for solutions to questions about Linked Lists */
object LinkedLists {
  // facilitates pattern matching
  private val LL = MutableLinkedList

  /** Question 2.1
    * Removes any duplicate entries within the list passed in as an argument
    *
    * @param list LinkedList containing possible duplicate parameters */
  def removeDuplicates[A](list: MutableLinkedList[A]): Unit = {

    // dictionary to keep check of nodes that already exist
    val existingNodes = collection.mutable.Map[A,A]()

    // imperative solution that removes values from the list that is passed
    def removeDuplicatesFromList(list: MutableLinkedList[A], existingNodes: collection.mutable.Map[A,A]): Unit = {
      var node = list
      var previous = list

      // shift the next reference of any keys before duplicates
      while(!node.isEmpty){ // scala's last node.next will always be empty never null
        if(existingNodes contains node.elem) {
          previous.next = node.next
        }
        else {
          existingNodes(node.elem) = node.elem
          previous = node
        }
        node = node.next
      }
    }
    if(list != null) removeDuplicatesFromList(list, existingNodes)
  }

  /** Question 2.2
    *
    * Finds the nth from last element from the LinkedList list
    *
    * @param list LinkedList to be searched
    * @return element Some(A) where is contained at the nth from last position or None if the list is null or smaller
    *         in length than n */
  def getNthFromLastElement[A](list: MutableLinkedList[A], n: Int): Option[A] = {

    // gets the lead node that is n-1 nodes away from the lag node, if n is greater than list length
    // if the list is smaller in length than n, an empty list is returned
    def getLeadNode(list: MutableLinkedList[A], n: Int): MutableLinkedList[A] = {
      @tailrec def getNthLeadNode(list: MutableLinkedList[A], stop: Int, current: Int): MutableLinkedList[A] = list match {
        case LL() => MutableLinkedList()
        case _ => if (current == stop) list else getNthLeadNode(list.next, stop, current + 1)
      }
      getNthLeadNode(list, n, 0)
    }

    // forwards lead and lag node until the lead hits the end and returns the lag element
    @tailrec def getLastLagElement(lead: MutableLinkedList[A], lag:MutableLinkedList[A]): A = lead match {
      case LL() => lag.head
      case _ => getLastLagElement(lead.next, lag.next)
    }

    // create lead lag nodes with a distance of n-1
    val lag = list
    val lead = getLeadNode(list, n)

    if(list == null || lead.isEmpty) None else Some(getLastLagElement(lead, lag))
  }

  /** Question 2.3
    *
    * Removes the given LinkedList node from its list
    *
    * @param node the node to be removed from the list */
  def removeNode[A](node: MutableLinkedList[A]): Unit = {

    // simply copy the data and reference from its neighbour node
    if(!(node == null || node.next == null)) {
      node.elem = node.next.elem
      node.next = node.next.next
    }
  }

  /** Question 2.4
    *
    * Sums two numbers represented in reversed lists and returns the sum in the reversed list
    * @param num1 the first number represented as a reversed list
    * @param num2 the second number represented as a reversed list
    * @return the sum of the two numbers represented as a reversed */
  def sumNumbersAsLists(num1: MutableLinkedList[Int], num2: MutableLinkedList[Int]): MutableLinkedList[Int] = {

    // sums two digits together and returns a the new digit with a carry if necessary
    def sumDigits(dig1: Int, dig2: Int, carry: Int): (Int, Int) = {

      // only single digits
      if(dig1 > 9 || dig2 > 9) throw new IllegalArgumentException("digits can not be greater than 9")

      // take the sum and return a the value for the position with a carry if needed
      val sum = dig1 + dig2 + carry
      if (sum > 9) (sum % 10, 1) else (sum, 0)
    }


    // runs the summation of the two lists
    @tailrec def sumNumbersAsListsHelper(num1: MutableLinkedList[Int],
                                num2: MutableLinkedList[Int],
                                acc: MutableLinkedList[Int],
                                carry: Int): MutableLinkedList[Int] = (num1,num2) match {

      // if both lists were  the same size return the new number
      case (LL(), LL()) => acc.reverse

      // if the lists are not the same size then ensure the last carries are carried out
      case (LL(), _) => {
        if(carry > 0 ){
          val sumCarry = sumDigits(0, num2.elem, carry)
          sumNumbersAsListsHelper(num1, num2.next, MutableLinkedList(sumCarry._1) append acc, sumCarry._2)
        }
        else acc.reverse append num2
      }
      case (_, LL()) => {
        if(carry > 0 ){
          val sumCarry = sumDigits(num1.elem, 0, carry)
          sumNumbersAsListsHelper(num1.next, num2, MutableLinkedList(sumCarry._1) append acc, sumCarry._2)
        }
        else acc.reverse append num1
      }

      // append the sum and forward the carry for the next digit
      case (_, _) =>{
        val sumCarry = sumDigits(num1.elem, num2.elem, carry)
        sumNumbersAsListsHelper(num1.next, num2.next, MutableLinkedList(sumCarry._1) append acc, sumCarry._2)
      }
    }

    sumNumbersAsListsHelper(num1, num2, MutableLinkedList(), 0)
  }

  /** Question 2.5
    *
    * Finds the beginning node of a loop if the linked list is circular
    * @param list a possibly circular linked list
    * @return Some(node) if the list is indeed circular, None otherwise */
  def getFirstLoopNode[A](list: MutableLinkedList[A]): Option[MutableLinkedList[A]] = {

    // keep moving the hare forward twice as fast the tortoise and they should intersect, None indicates non-circularity
    @tailrec def findNodeInLoop(tortoise: MutableLinkedList[A], hare: MutableLinkedList[A]): Option[MutableLinkedList[A]] = {
      if (hare.isEmpty) None
      else if (hare eq tortoise) Some(hare) else findNodeInLoop(tortoise.next, hare.next.next)
    }

    // find the beginning of the loop, this function is inherently reliant on the nodeInLoop being discovered
    // through a tortoise and hare algorithm where the hare moves twice as fast as the tortoise
    @tailrec def findLoopBeginning(list: MutableLinkedList[A], nodeInLoop: MutableLinkedList[A]): MutableLinkedList[A] = {
      if(list eq nodeInLoop) list else findLoopBeginning(list.next, nodeInLoop.next)
    }
    val nodeInLoop = findNodeInLoop(list.next, list.next.next)

    nodeInLoop match {
      case None => None
      case Some(intersection) => Some(findLoopBeginning(list, intersection))
    }
  }
}
