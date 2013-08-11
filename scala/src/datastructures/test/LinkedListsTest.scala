package datastructures.test

import datastructures.LinkedLists
import org.scalatest.FunSuite

/** Test class for Arrays and Strings solutions in datastructures.LinkedList */
class LinkedListsTest extends FunSuite{

  /** tests if duplicates are removed from the linked list successfully */
  test("Duplicates are removed from LinkedList"){
    val list = new scala.collection.mutable.LinkedList[Int]


    LinkedLists.removeDuplicates()
  }
}
