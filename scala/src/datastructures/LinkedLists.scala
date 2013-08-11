package datastructures

import scala.collection.mutable.LinkedList
import scala.annotation.tailrec

/** Container object for solutions to questions about Linked Lists */
object LinkedLists {

  /** Question 2.1
    * Removes any duplicate entries within the linkedlist passed in as an argument
    *
    * @param list LinkedList containing possible duplicate parameters
    */
  def removeDuplicates[A](list: LinkedList[A]): Unit = {

    // dictionary to keep check of nodes that already exist
    val existingNodes = collection.mutable.Map[A,A]()

    // imperative solution that removes values from the list that is passed
    def removeDuplicatesFromList(list: LinkedList[A], existingNodes: Map[A,A]): Unit = {
      var node = list
      var previous = list

      // shift the next reference of any keys before duplicates
      while(node != null){
        if(existingNodes.containsKey(node)) {
          previous.next = node.next
        }
        else {
          existingNodes(node) = node
          previous = node
        }
        node = node.next
      }
    }
    // a more functional solution using and accumulator and a mutable list
    @tailrec def removeDuplicatesFunctional(list: List[A], acc: List[A], existingNodes: Map[A,A]): List[A] = list match {
      case Nil => acc.reverse
      case head::tail => if(existingNodes.contains(head)) removeDuplicatesFunctional(tail, acc, existingNodes)
        else {
        existingNodes(head) = head
        removeDuplicatesFunctional(tail, head::acc, existingNodes)
      }
    }
    if(list != null) removeDuplicatesFromList(list, existingNodes)
  }


}
