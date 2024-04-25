package Model

import scala.collection.mutable.Queue
huybranch
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

  def fieldPrepare(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]) : Unit = {
    for (_ <- 1 to 4) { //  each player gets 5 cards initially
      val card = playerHand.dequeue()
      playerField += card
      playerHand.drop(1)
    }
  }
import scala.util.Random

class PlayingField(player1Cards: Queue[Card], player2Cards: Queue[Card]) {
  private var player1Hand: Queue[Card] = Queue.empty
  private var player2Hand: Queue[Card] = Queue.empty
  private var player1Field: Queue[Card] = Queue.empty
  private var player2Field: Queue[Card] = Queue.empty

  // Initialize players' hands and fields
  private def initialize(): Unit = {
    player1Hand = drawCards(player1Cards, 4)
    player2Hand = drawCards(player2Cards, 4)
  }

  // Draw cards from the given queue
  private def drawCards(cards: Queue[Card], count: Int): Queue[Card] = {
    cards.dequeueAll(_ => true).take(count)
  }

  // Display players' hands and fields
branch-master
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
huybranch
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


  // Play the game
  def playGame(): Unit = {
    initialize()
    display()

    // Player 1 attacks Player 2 with a card from their hand
    val player1AttackCard = player1Hand.dequeue()
    player2Field.enqueue(player1AttackCard)

    println(s"\nPlayer 1 attacks with: $player1AttackCard")
    display()

    // Implement further game logic here
  }
}

object PlayingField {
  def main(args: Array[String]): Unit = {
    // Sample queues of cards for players
    val player1Cards = Queue(
      Card("Ace", Suit.Hearts), Card("2", Suit.Hearts), Card("3", Suit.Hearts), Card("4", Suit.Hearts),
      Card("5", Suit.Hearts), Card("6", Suit.Hearts), Card("7", Suit.Hearts), Card("8", Suit.Hearts),
      Card("9", Suit.Hearts), Card("10", Suit.Hearts), Card("Jack", Suit.Hearts), Card("Queen", Suit.Hearts),
      Card("King", Suit.Hearts)
    )
    val player2Cards = Queue(
      Card("Ace", Suit.Clubs), Card("2", Suit.Clubs), Card("3", Suit.Clubs), Card("4", Suit.Clubs),
      Card("5", Suit.Clubs), Card("6", Suit.Clubs), Card("7", Suit.Clubs), Card("8", Suit.Clubs),
      Card("9", Suit.Clubs), Card("10", Suit.Clubs), Card("Jack", Suit.Clubs), Card("Queen", Suit.Clubs),
      Card("King", Suit.Clubs)
    )

    val playingField = new PlayingField(player1Cards, player2Cards)
    playingField.playGame()
    branch-master
  }
}
