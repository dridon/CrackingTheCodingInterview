package datastructures.stacksandqueues

/** Question 3.4
 *
 * A set of classes that solve the towers of hanoi problem
 *
 * User: faiz
 * Date: 26/08/13
 * Time: 12:57 PM
 */
/**
 * Disc container
 * @param size the size of the disc
 */
case class Disc(size: Int)

/**
 * A tower to move discs from and to
 *
 * @param label the name of the tower
 */
class Tower(val label: String) {
  private val discs = new scala.collection.mutable.Stack[Disc]

  /**
   * Adds a disc to the top of the tower, the disc must be smaller than all other discs on the tower
   * @param d Disc to be added
   * @return
   */
  def addDisc(d: Disc) = {
    if(discs.isEmpty || d.size < discs.top.size) discs.push(d)
    else throw new Exception("Invalid move, can't move bigger disc on top of smaller disc")
  }

  /**
   * The number of discs currently on the tower
   * @return the number of discs on the tower
   */
  def discCount(): Int = discs.length

  /**
   * The disc at the index
   * @param index the index to retrieve the disc from
   * @return Some(Disc) if the index is defined otherwise None
   */
  def discAt(index: Int): Option[Disc] = if(discs.isDefinedAt(index)) Some(discs(index)) else None

  /**
   * Removes a disc from the top of the tower
   * @return Some(Disc) from the top of the tower, None if the tower is empty
   */
  def removeDisc(): Option[Disc] = if(discs.isEmpty) None else Some(discs.pop())

  /**
   * Checks if the tower is empty
   * @return True if the tower is empty, false otherwise.
   */
  def isEmpty: Boolean = discs.isEmpty

  /**
   * Populates the tower with Discs of sizes 0 until n
   * @param n the number of discs to populate the tower with
   */
  def populate(n: Int): Unit = for (i<- n-1 to 0 by -1) addDisc(new Disc(i))
}

/**
 * A towers of Hanoi solver
 * @param verbose a higher verbosity level increases the number of messages being printed
 */
class TowersOfHanoi(val verbose: Int = 0) {

  // moves a disc from a source tower to a destination tower
  private def moveDisc(source: Tower, destination: Tower): Unit = source.removeDisc() match {
    case Some(d) => moves += 1; destination.addDisc(d)
    case None => throw new Exception("Invalid move, tower is empty")
  }

  /**
   * Moves the discs from one tower to the other
   * @param n the number of discs to move
   * @param source the source tower containing the disc
   * @param destination the destination tower to move the discs
   * @param spare the spare tower to help facilitate the move
   *
   * @throws Exception if the spare and destination towers can not accommodate the sizes of the larger discs
   */
  def moveDiscs(n: Int, source: Tower, destination: Tower, spare: Tower):Unit = {

    // recursive solution to Towers of Hanoi
    def moveDiscsRecursively(n: Int, source: Tower, destination: Tower, spare: Tower): Unit = {
      if(n == 0){
        moveDisc(source, destination)
        printMoveDisc(n, source, destination)
      }
      else {

        printMove(n-1, source, spare, destination)
        moveDiscsRecursively(n-1, source, spare, destination)


        moveDisc(source, destination)
        printMoveDisc(n, source, destination)

        printMove(n-1, spare, destination, source)
        moveDiscsRecursively(n-1, spare, destination, source)
      }
    }

    // checks to see if a tower can accomodate the move of discs of size n
    def canMoveToTower(size :Int, t: Tower) = t.isEmpty || (t.discAt(t.discCount() -1) match {
      case Some(d) => d.size >= size
      case None => true
    })

    // ensure discs can be moved properly and reset move count
    if(!(canMoveToTower(n, spare) && canMoveToTower(n, destination)))
      throw new Exception("Spare or Destination can't accommodate such a move")
    moves = 0

    // solve the towers
    moveDiscsRecursively(n - 1, source, destination, spare)
  }



  // tracks the number of movers per solution
  private var moves = 0

  /**
   * The number of moves used to solve the previous run of the towers of hanoi
   * @return the number of moves used to solve the previous run
   */
  def totalMovesUsed():Int = moves

  // prints physical moves
  private def printMoveDisc(n: Int, source: Tower, destination: Tower):Unit = {
    if(verbose > 0) println("Physically moving disc from tower\t" + source. label + "\tto tower\t" + destination.label)

    if(verbose > 0) println("Total moves: \t" + moves)
  }

  // prints recursive moves
  private def printMove(n: Int, source: Tower, destination: Tower, spare: Tower): Unit = {
    if (verbose > 0) println("Moving disc\t" + n
                             + "\tfrom tower\t" + source.label
                             + "\tto tower\t" + destination.label
                             + "\tvia tower\t" + spare.label)
  }
}
