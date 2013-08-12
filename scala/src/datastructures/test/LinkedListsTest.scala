package datastructures.test

import datastructures.LinkedLists
import org.scalatest.FunSuite
import scala.collection.mutable.{ LinkedList => MutableLinkedList}

/** Test class for Arrays and Strings solutions in datastructures.LinkedList */
class LinkedListsTest extends FunSuite{

  /** tests if duplicates are removed from the linked list successfully */
  test("Duplicates are removed from LinkedList"){
    val list = MutableLinkedList(1,2,1,1,1,1,2,3,4,3,3,5,6)
    val noDuplicatesList = MutableLinkedList(1,2,3,4,5,6)

    LinkedLists.removeDuplicates(list)
    assert(list == noDuplicatesList)
  }

  /** tests if the proper nth from last element is fetched properly */
  test("Nth element is fetched"){
    val list = MutableLinkedList(1,2,3,4,5,6,7,8,9,10)
    val testValue = 8
    val n = 3
    val nthElement = LinkedLists getNthFromLastElement (list, n) match {
      case None => - 1
      case Some(x) => x
    }

    assert(nthElement == testValue)
  }

  /** tests if the passed node is removed from the list */
  test("Specific node that is passed as a parameter is removed from its list"){
    val list = MutableLinkedList(1,2,3,4,5,6)
    val resultList = MutableLinkedList(1,2,4,5,6)
    val specificNode = list.next.next
    LinkedLists.removeNode(specificNode)
    assert(list == resultList)
  }

  /** tests if two numbers represented as a list are added properly */
  test("Two numbers as lists are added properly and returned as list"){
    val num1 = MutableLinkedList(3,1,5)
    val num2 = MutableLinkedList(5,9,2)
    val result12 = MutableLinkedList(8,0,8)

    // different orders of magnitude
    val num3 = MutableLinkedList(0,5,2,2)
    val num4 = MutableLinkedList(0,6)
    val result34 = MutableLinkedList(0,1,3,2)

    val sum12 = LinkedLists sumNumbersAsLists (num1, num2)
    val sum34 = LinkedLists sumNumbersAsLists (num3, num4)
    val sum43 = LinkedLists sumNumbersAsLists (num4, num3)


    assert(sum12 == result12)
    assert(sum34 == result34)
    assert(sum43 == result34)
  }

  /** tests if a circular linked list is properly detected */
  test("Circular linked lists are properly detected"){
    val list1 = MutableLinkedList(1,2,3,4)
    val list2 = MutableLinkedList(5,6,7)
    val list3 = MutableLinkedList(8,9,10)
    val list4 = list1 append list2 append list3 append list2

    val circularNode = LinkedLists getFirstLoopNode list4  match {
      case None => MutableLinkedList()
      case Some(list) => list
    }

    assert( circularNode eq list2 )


  }
}
