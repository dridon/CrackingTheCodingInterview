package datastructures.stacksandqueues

/** Question 3.1
  *
  * This class acts as a proxy for 3 stacks.
  *
  * User: faiz
  * Date: 19/08/13
  * Time: 5:10 PM */

 class ThreeStacks[A, B, C ](val stackSize:Int  = 1000) {
  // the shared stacks
  val stack1 = SharedStack[A]()
  val stack2 = SharedStack[B]()
  val stack3 = SharedStack[C]()

  // the shared stack class is simply a container for push and pop functions
  class SharedStack[T](val push: T => Unit, val pop: () => Option[T])

  // the sharedstack object is a factory setups a shared stack object with its proper push/pop functions that are received
  // from the stackmemory instance
  private object SharedStack{
    val stackMemory = new StackMemory(stackSize)

    def apply[T](): SharedStack[T] = {
      val stackTuple = stackMemory.registerStack[T]()
      new SharedStack(stackTuple._1, stackTuple._2)
    }
  }


  // the memory object responsible for handling the sharing of the array
  private class StackMemory(val stackSize: Int = 1000){

    // helps prevent the conflict warnings with immutable and mutable collections
    private val MutableMap = scala.collection.mutable.Map

    // the array that contains the stack nodes
    private val array = new Array[Option[StackNode[Any]]](stackSize)


    // keeps track of the heads of all the stacks
    private val registeredStackHeads = MutableMap[Int, Int]()

    // an index to help assign every new stack a unique key
    private var nextRegistrationIndex = 1

    // a list of indices in the array that have been freed
    private var freedIndices = List[Int]()

    // the next available free index, increments till it reaches the end of the array, different from the freeIndices list
    // in that it only is concerned with free cells while they are available at the end of the array
    private var nextFreeIndex = 0


    /**
     * Registers a stack within the memory object and returns the push/pop functions
     * @return the push/pop functions relevant for that stack */
    def registerStack[T](): (T  => Unit, () => Option[T]) = {
      val index = nextRegistrationIndex
      val noPreviousValueIndex = -1

      // register the index in the stack heads with an empty value
      registeredStackHeads(index) = noPreviousValueIndex

      nextRegistrationIndex += 1
      (push[T](index), pop[T](index) )
    }

    /**
     * Returns the push function for a stack at the given index
     * @param stackIndex the index of the requesting stack
     * @return  the push function for the stack */
    def push[T](stackIndex: Int) = {
      (value: T) =>  {

        // get the indices and initialize a stack node
        val headIndex = registeredStackHeads(stackIndex)
        val index = nextAvailableIndex()
        val stackNode = new StackNode[T](headIndex, value)

        // store the new stack head and place it in to the array
        registeredStackHeads(stackIndex) = index
        array(index) = Some(stackNode)
      }
    }

    /**
     * The pop function for the stack at a given index
     * @param stackIndex  the index of the requesting stack
     * @return the pop function for the stack */
    def pop[T](stackIndex: Int): () => Option[T] = {
      () => {
        val headIndex = registeredStackHeads(stackIndex)

        if(headIndex < 0) None else {
          array(headIndex) match {
            case Some(s : StackNode[T]) => {
              registeredStackHeads(stackIndex) = s.previousIndex
              freeIndex(headIndex)
              Some(s.value)
            }
            case _ => None
          }
        }
      }
    }

    // retrieve the next available index, throws an exception if there are no available indices
    private def nextAvailableIndex():Int = {

      // return a new index if the array isn't full otherwise throw an exception
      def newIndex() : Int = {
        if(nextFreeIndex >= stackSize){
          throw new Exception("No more room in stack is available")
        }
        else{
          val index = nextFreeIndex
          nextFreeIndex += 1
          index
        }
      }

      // first check if any of the old indices have been freed
      freedIndices match {
        case Nil => newIndex()
        case head::tail => freedIndices = tail; head
      }
    }

    // frees an index and adds it to the free indices list
    private def freeIndex(i: Int): Unit = {
      if (i >= array.length) throw new ArrayIndexOutOfBoundsException("The index to be freed does not exist in the array")
      array(i) = None
      freedIndices = i::freedIndices
    }

    // covariant node that helps keep track of the previous index of the stack
    private class StackNode[+S](val previousIndex: Int, val value: S)

  }
}
