package Model

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class PlayingField(player1Cards: mutable.Queue[Card], player2Cards: mutable.Queue[Card]) {
  private val player1Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player2Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player1Field: ListBuffer[Card] = ListBuffer.empty
  private val player2Field: ListBuffer[Card] = ListBuffer.empty

  def gamePrepare(): Unit = {
    val deck = CardDeck.createStandardDeck()
    CardDeck.shuffleDeck(deck)

    // Distribute cards to player 1
    for (_ <- 1 to 26) {
      val card = deck.dequeue()
      player1Hand.enqueue(card)
    }

    // Distribute cards to player 2
    for (_ <- 1 to 26) {
      val card = deck.dequeue()
      player2Hand.enqueue(card)
    }

    // Prepare fields for both players
    fieldPrepare(player1Hand, player1Field)
    fieldPrepare(player2Hand, player2Field)
  }

  def getPlayer1Hand: mutable.Queue[Card] = player1Hand

  def getPlayer2Hand: mutable.Queue[Card] = player2Hand

  def getPlayer1Field: ListBuffer[Card] = player1Field

  def getPlayer2Field: ListBuffer[Card] = player2Field

  protected[Model] def fieldPrepare(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]): Unit = {
    for (_ <- 1 to 4) { // each player gets 4 cards initially
      val card = playerHand.dequeue()
      playerField += card
    }
  }

  private def display(): Unit = {
    println("Player 1's hand:")
    player1Hand.foreach(println)
    println("\nPlayer 1's field:")
    player1Field.foreach(println)
    println("\nPlayer 2's hand:")
    player2Hand.foreach(println)
    println("\nPlayer 2's field:")
    player2Field.foreach(println)
  }

  // Play the game
  def playGame(): Unit = {
    gamePrepare()
    display()
  }
}
