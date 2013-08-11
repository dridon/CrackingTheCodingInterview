package datastructures

import scala.annotation.tailrec

/** Container object for solutions to questions about Arrays and Strings */
object ArraysAndStrings {

  // converts a string to a list and throws an exception for a null string 
  private def stringToList(s: String): List[Char] = s match {
    case null => throw new NullPointerException("String may not be null")
    case _ => s.toList
  }

  // converts an array of character to a list of characters and throws an exception for a null array
  private def charArrayToList(charArray: Array[Char]): List[Char] = charArray match {
    case null => throw new NullPointerException("Array may not be nul")
    case _ => charArray.toList
  }

  /** Question 1.1
   * Returns a boolean value indicating whether or not the input string s has all unique characters or not. 
   * It does not ignore whitespace. 
   *
   * @param s string to be checked for unique characters 
   * @return true/false value indicating string uniqueness 
   *
   * @throws NullPointerException if the string s is null
   */
  def allUniqueCharacters(s: String): Boolean = {

    // a string of size 1 is always unique 
    val alwaysUniqueSize = 1

    def allUniqueCharactersReverseMultiCharacterString(s: String) = {
      // character set size, increase for unicode strings
      val charSetSize = 256

      // define an array for character codes
      val repeatedCharacters = new Array[Boolean](charSetSize)

      // Get a list of characters for a more functional design. This ends up using a list instead of
      // String.charAt() and adds O(n) time.
      val stringChars = stringToList(s)

      // cast characters to int, since in this case the character itself will reference the index
      implicit def charToInt(x: Char): Int = x.asInstanceOf[Int]

      // determine if all the characters in the string are unique. Tail recursive. runs in O(n) time.
      @tailrec def uniqueCharactersInString(repeatedCharacters: Array[Boolean], cList: List[Char]): Boolean = cList match {
        case Nil => true
        case head :: tail =>
          if (repeatedCharacters(head)) false
          else {
            repeatedCharacters(head) = true
            uniqueCharactersInString(repeatedCharacters, tail)
          }
      }

      /* alternative, more imperative, implementation of uniqueCharactersInString that doesn't use a List */
      def uniqueCharactersInStringImperative(repeatedCharacters: Array[Boolean], s: String): Boolean = {
        var unique = true
        for (i <- 0 until s.length() if unique) {
          val charAtI = s.charAt(i)
          if (repeatedCharacters(charAtI)) unique = false else repeatedCharacters(charAtI) = true
        }
        unique
      }

      /*// uncomment this block and comment the line after this for a purely imperative solution
       * uniqueCharactersInStringImperative(repeatedCharacters, s)*/
      uniqueCharactersInString(repeatedCharacters, stringChars)
    }
    if (s.length() < alwaysUniqueSize) true else allUniqueCharactersReverseMultiCharacterString(s)
  }

  /** Question 1.2
   * Returns a reverse of the C-Style string of the input parameter cString. A C-Style string is treated 
   * as a Scala character array with a null character '\0' appended to it
   *
   * @param cString a C-Style string
   * @return reverse of cString    
   *
   * @throws NullPointerException if the array cString is null
   */
  def reverseCStyleString(cString: Array[Char]): Array[Char] = {

    // cString as character list 
    val charList = charArrayToList(cString)

    // reverse a char list with an accumulator 
    def reverseCharList(cList: List[Char], cAcc: List[Char]): Array[Char] = cList match {
      case Nil => cAcc.toArray
      case '\0' :: Nil => reverseCharList(Nil, cAcc)
      case head :: tail => reverseCharList(tail, head :: cAcc)
    }

    reverseCharList(charList, List('\0'))
  }

  /** Question 1.3
   * Returns a version of the string s that only keeps the first occurrence of 
   * duplicate characters 
   *
   * @param s the string with potential duplicate characters
   * @return a version of s without any repeating characters  
   * @throws NullPointerException if the string s is null 
   */
  def withoutDuplicates(s: String): String = {
    // character set size, increase for unicode strings 
    val characterSetSize = 256

    // array to record a characters indices 
    val charAppeared = new Array[Boolean](characterSetSize)

    // get characters of the string as a vector, immutable but O(1) index accessible 
    val stringChars = stringToList(s)

    // cast characters to int, since in this case the character itself will reference the index 
    implicit def charToInt(x: Char): Int = x.asInstanceOf[Int]

    // create a string without duplicates 
    @tailrec def stringWithoutDuplicates(charAppeared: Array[Boolean], charList: List[Char], charAcc: List[Char]): String = charList match {
      case Nil => charAcc.reverse.mkString("")
      case head :: tail =>
        if (!charAppeared(head)) {
          charAppeared(head) = true
          stringWithoutDuplicates(charAppeared, tail, head :: charAcc)
        } else stringWithoutDuplicates(charAppeared, tail, charAcc)
    }
    stringWithoutDuplicates(charAppeared, stringChars, List[Char]())
  }

  /** Question 1.4
   * Checks to see if two strings are anagrams of each other. 
   *
   * @param string1 first string to be compared
   * @param string2 second string to be compared
   * @return boolean indicating whether string1 and string2 are anagrams of each other  
   */
  def areAnagrams(string1: String, string2: String): Boolean = {

    // sort the string and return an empty string if its null 
    def sortLowerCasedString(s: String) = s match {
      case null => throw new NullPointerException("String may not be null")
      case _ => s.toLowerCase.sorted
    }

    // Sort the strings in O(nlog n) time
    val sortedString1 = sortLowerCasedString(string1)
    val sortedString2 = sortLowerCasedString(string2)

    // if the sorted strings are equal then they are anagrams 
    sortedString1 == sortedString2
  }

  /** Question 1.5
   * Replaces all spaces in a string with %20
   *
   * @param s the string with spaces to be encoded
   * @return String that requires %20 input
   * @throws NullPointerException if the string is null
   */
  def replaceAllSpacesWithUrlEncoding(s: String): String = {

    // get a map of all url encodings
    val urlEncodings = Map(" " -> "%20")

    // convert char to string 
    implicit def charToString(c: Char): String = c.toString

    // val string to list of char 
    val charList = stringToList(s)

    // a general function to replace all special characters with their specific url encodings 
    @tailrec def replaceStringWithUrlEncodings(urlEncodings: Map[String, String], charList: List[Char], stringAcc: List[String]): String = charList match {
      case Nil => stringAcc.reverse.mkString
      case head :: tail =>
        if (urlEncodings.contains(head)) replaceStringWithUrlEncodings(urlEncodings, tail, urlEncodings(head) :: stringAcc)
        else replaceStringWithUrlEncodings(urlEncodings, tail, head :: stringAcc)
    }

    replaceStringWithUrlEncodings(urlEncodings, charList, List())
  }

  /** Question 1.6
   * Rotate a matrix by 90 degrees
   *
   * @param matrix a NxN matrix of 32 bit integers representing 4 bytes
   *
   * @throws IllegalArgumentException if the argument is not a nxn matrix 
   * @throws NullPointerException if the matrix array is null
   */
  def rotateMatrix90Degrees(matrix: Array[Array[Int]]): Unit = {

    if (matrix == null) throw new NullPointerException("Matrix may not be null")

    // the matrix is evaluated only if its not null and if its square
    val size = if (matrix.length == 0 || (matrix.length > 0 && matrix.length == matrix(0).length)) matrix.length
    else throw new IllegalArgumentException("Matrix is not a NxN array")


    def newPosition(i: Int, j: Int, length: Int): (Int, Int) = {
      // the rotation is around the right diagonal, so get the diagonal position on row i
      val rowRightDiagonalX = i
      val rowRightDiagonalY = length - i - 1

      // the new column is the same as the diagonal entries column,
      // the new row is the distance rowRightDiagonalY - j from the diagonal row
      (rowRightDiagonalX + rowRightDiagonalY - j, rowRightDiagonalY)
    }

    // a swap method that takes advantages of the special property of int 
    def swapIntValues(i: Int, j: Int, iSwap: Int, jSwap: Int, matrix: Array[Array[Int]]): Unit = {

      //swap entries if they are in different cells  
      if (!(i == iSwap && j == jSwap)) {
        // a1 = a0 + b0
        // b1 = a1 - b0 = a0
        // a1 = a1 - b1 = b0
        matrix(i)(j) = matrix(i)(j) + matrix(iSwap)(jSwap)
        matrix(iSwap)(jSwap) = matrix(i)(j) - matrix(iSwap)(jSwap)
        matrix(i)(j) = matrix(i)(j) - matrix(iSwap)(jSwap)
      }
    }

    // general 90 degree rotation function 
    def rotateMatrix90DegreesHelper(matrix: Array[Array[Int]], newPosition: (Int, Int, Int) => (Int, Int), swap: (Int, Int, Int, Int, Array[Array[Int]]) => Unit) = {

      for (j <- 0 until matrix.length) {
        for (i <- 0 until matrix.length - j) {
          val newXY = newPosition(i, j, matrix.length)
          swap(i, j, newXY._1, newXY._2, matrix)
        }
      }
    }
    rotateMatrix90DegreesHelper(matrix, newPosition, swapIntValues)
  }

  /** Question 1.7
   * Zeros an entire row and column of a matrix if a single entry on that column is zero
   *
   * @param matrix an MxN matrix that may have random zero entries throughout
   *
   * @throws NullPointerException matrix may not be null
   */
  def zeroRowColumnOfZeroEntries(matrix: Array[Array[Int]]): Unit = {
    if(matrix == null) throw new NullPointerException

    // the definition of what part is the and what part is the column doesn't really matter in this case
    val rowCount = matrix.length
    val colCount = if(matrix.length == 0 ) 0 else matrix(0).length

    // track which rows have been zeroed
    val zeroedRows = new Array[Boolean](rowCount)
    val zeroedColumns = new Array[Boolean](colCount)

    // takes the value of an entry and spreads to its entire column and row
    def putEntryValueInEntireRowAndColumn(matrix: Array[Array[Int]], i: Int, j: Int): Unit = {
      val entryValue = matrix(i)(j)
      for(x <- 0 until matrix.length){
        matrix(x)(j) = entryValue
      }

      for(y <- 0 until matrix(0).length){
        matrix(i)(y) = entryValue
      }
    }

    // zeroes all columns and rows that have a zero entry
    def zeroRowColumnWithZeroEntriesHelper( matrix: Array[Array[Int]],
                                            zeroedRows: Array[Boolean],
                                            zeroedColumns: Array[Boolean]) =  {
      for(i <- 0 until matrix.length){
        // skip a row if its been zeroed
          for(j <- 0 until matrix(0).length if !zeroedRows(i)){

            // skip any cells that are in an already zeroed column
            if(!zeroedColumns(j) && matrix(i)(j)==0){
              putEntryValueInEntireRowAndColumn(matrix, i, j)
              zeroedRows(i) = true
              zeroedColumns(j) = true
            }
          }
      }
    }

    zeroRowColumnWithZeroEntriesHelper(matrix, zeroedRows, zeroedColumns)
  }

  /** Question 1.8
   * Checks if string2 is a rotation of string1
   *
   * @param string1 first string to be compared
   * @param string2 second string to be compared
   *
   * @return boolean indicating if string2 is indeed a rotation of string1
   *
   * @throws NullPointerException if either string1 or string2 is null
   */
  def isRotation(string1: String, string2: String): Boolean = {
    if (string1 == null || string2 == null) throw new NullPointerException("Strings may not be null")

    // concatenating string1 with itself will contain any possible rotation
    val doubleString1 = string1 + string1

    // the required isSubstring function from the question
    def isSubstring(string1: String, string2: String): Boolean = string1.indexOf(string2) >= 0

    isSubstring(doubleString1, string2)
  }
}