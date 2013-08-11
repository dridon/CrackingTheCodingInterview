package datastructures.test

import org.scalatest.FunSuite
import datastructures.ArrayAndString

class ArrayAndStringsTest extends FunSuite {
  
  /** tests if characters are indeed unique */ 
  test("String is detected as having all unique characters") {
    val testString = "Myworldnice"
    assert(ArrayAndString.allUniqueCharacters(testString))
  }
  
  /** tests if characters are not unique */
  test("String has duplicate characters"){
    val testString = "dupplicate"
    assert(!ArrayAndString.allUniqueCharacters(testString))
  }
 
  /** tests if an array if a cstring is reversed properly */ 
  test("CString is reversed") {
    val toReverse = Array('r', 'e','v', 'e', 'r', 's', 'e', '\0')
    val reversed = Array('e', 's', 'r', 'e', 'v', 'e', 'r' , '\0')
    
    assert(ArrayAndString.reverseCStyleString(toReverse).sameElements(reversed))
  }   
  
  /** tests if all duplicates in a string are removed */ 
  test("Duplicate characters in string are removed"){
    val duplicateString = "aaabbbbcccccc"
    val uniqueCharString  = "abc"
      
    assert(ArrayAndString.withoutDuplicates(duplicateString)==uniqueCharString)
  }
  
  /** tests if two strings are anagrams */ 
  test("Two strings are anagrams"){
    val anagram1 = "Army" 
    val anagram2 = "Mary" 
      
    assert(ArrayAndString.areAnagrams(anagram1, anagram2))
  }
  
  /** tests if two strings are not anagrams */ 
  test("Two strings are not anagrams"){
    val notAnagram1 = "Jack" 
    val notAnagram2 = "Black" 
      
    assert(!ArrayAndString.areAnagrams(notAnagram1, notAnagram2))
  }
  
  /** tests if all spaces in a string are replaced with %20 */ 
  test("All spaces are replaced with %20"){
    val withSpaces = "this will have 3"
    val withPercent20 = "this%20will%20have%203" 

    assert(ArrayAndString.replaceAllSpacesWithUrlEncoding(withSpaces)==withPercent20)
  }
  
  /** tests the in-place rotation of a matrix */ 
  test("Matrix rotated by 90 degrees"){
    val matrix = Array(
        Array(1,1,1,1),
        Array(2,2,2,2),
        Array(3,3,3,3),
        Array(4,4,4,4))
        
    val rotatedMatrix = Array(
        Array(4,3,2,1),
        Array(4,3,2,1),
        Array(4,3,2,1),
        Array(4,3,2,1))
        

    ArrayAndString.rotateMatrix90Degrees(matrix)
    assert(matricesEqual(matrix, rotatedMatrix))
  }

  /** tests if all the entries in a row and column of an entry with a zero value within a matrix are set to zero as well */
  test("Rows and Columns zeroed properly for a zero entry"){
    val matrix = Array(
      Array(1,1,1,1,1),
      Array(2,0,2,2,2),
      Array(3,3,3,3,3),
      Array(4,4,4,0,4))

    val zeroedMatrix = Array(
      Array(1,0,1,0,1),
      Array(0,0,0,0,0),
      Array(3,0,3,0,3),
      Array(0,0,0,0,0))

    ArrayAndString.zeroRowColumnOfZeroEntries(matrix)
    assert(matricesEqual(matrix, zeroedMatrix))
  }

  /** tests if string2 is a rotation of string1 */
  test("String is rotation of another"){
    val string1 = "waterbottle"
    val string2 = "erbottlewat"

    assert(ArrayAndString.isRotation(string1, string2))
  }

  /** tests if string2 is not a rotation of string1 */
  test("String should not be a rotation of another"){
    val string1 = "rotation"
    val string2 = "otation2"

    assert(!ArrayAndString.isRotation(string1, string2))
  }

  // checks if two matrices are equal
  private def matricesEqual(array1: Array[Array[Int]], array2: Array[Array[Int]]):Boolean = {
    var areEqual =
      (array1.length == 0 && array2.length == 0) ||
        (array1.length == array2.length && array1(0).length == array2(0).length)

    for (i <- 0 until array1.length if areEqual){
      for(j<-0 until array2.length if areEqual){
        areEqual = array1(i)(j) == array2(i)(j)
      }
    }
    areEqual
  }

}