object Main extends App { // Extend App to make the class executable
  object Count {
    var countCards: Int = 0
  }

  // Define createDeck method here or make it accessible
  // to the main method
  def createDeck(): List[String] = {
    val suits_list = List("Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack").flatMap(number => List("Hearts",
      "Diamonds", "Spades", "Clubs").map(suit => s"$number of $suit"))

    suits_list
  }

  println(Count.countCards) // Accessing the global variable

  // Use suits_list instead of createDeck()
  for (x <- createDeck()) {
    println(x)
    Count.countCards += 1
  }
  println(Count.countCards)
}
