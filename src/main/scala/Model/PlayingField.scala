package Model

import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.util.Random
import CardObject._
import Suit._
import CardDeck._
import CardValueOrdering._

import scala.collection.mutable

class PlayingField(player1Cards: Queue[Card], player2Cards: Queue[Card]) {
  private val player1Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player2Hand: Queue[Card] = Queue.empty
  private val player1Field: ListBuffer[Card] = ListBuffer.empty
  private val player2Field: ListBuffer[Card] = ListBuffer.empty
  def gamePrepare() : Unit = {
    val deck = CardDeck.createStandardDeck()
    CardDeck.shuffleDeck(deck)
    // Distribute cards to player 1
    for (_ <- 1 to 26) { // Assuming each player gets 5 cards initially
      val card = deck.dequeue()
      player1Hand.enqueue(card)
    }
    // Distribute cards to player 2
    for (_ <- 1 to 26) {
      val card = deck.dequeue()
      player2Hand.enqueue(card)
    }
  }

  def getPlayer1Hand: mutable.Queue[Card] = player1Hand

  def getPlayer2Hand: mutable.Queue[Card] = player2Hand

  def fieldPrepare(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]) : Unit = {
    for (_ <- 1 to 4) { //  each player gets 5 cards initially
      val card = playerHand.dequeue()
      playerField += card
      playerHand.drop(1)
    }
  }
  def display(): Unit = {
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
  }
}
object PlayingField {
  def main(args: Array[String]): Unit = {

    val player1Cards = mutable.Queue.empty[Card]
    val player2Cards = mutable.Queue.empty[Card]

    val playingField = new PlayingField(player1Cards, player2Cards)
    playingField.gamePrepare()
    playingField.fieldPrepare(playingField.player1Hand,playingField.player1Field)
    playingField.fieldPrepare(playingField.player2Hand,playingField.player2Field)

    playingField.display()

  }
}
