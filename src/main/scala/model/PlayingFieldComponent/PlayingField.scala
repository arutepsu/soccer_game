package model.PlayingFieldComponent

import model.CardComponent.CardDeck.*
import model.CardComponent.CardObject.*
import model.CardComponent.{Card, CardDeck}

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Queue}
import scala.util.{Failure, Success, Try}

class PlayingField(private var player1Cards: mutable.Queue[Card], private var player2Cards: mutable.Queue[Card]) {
  var player1Hand: mutable.Queue[Card] = player1Cards.clone()
  var player2Hand: mutable.Queue[Card] = player2Cards.clone()
  val player1Field: ListBuffer[Card] = ListBuffer.empty
  val player2Field: ListBuffer[Card] = ListBuffer.empty

  def setPlayer1Cards(cards: mutable.Queue[Card]): Unit = {
    player1Cards = cards
  }

  def setPlayer2Cards(cards: mutable.Queue[Card]): Unit = {
    player2Cards = cards
  }

  def gamePrepare(): Try[Unit] = {
    Try {
      val deck = CardDeck.createStandardDeck()
      CardDeck.shuffleDeck(deck)
      assert(deck.size == 52, s"Deck size is ${deck.size}, expected 52")

      player1Hand.clear()
      player2Hand.clear()
      player1Field.clear()
      player2Field.clear()

      // Deal 26 cards to each player
      for (_ <- 1 to 26) {
        player1Hand.enqueue(deck.dequeue())
        player2Hand.enqueue(deck.dequeue())
      }

      // Deal 4 cards to each player's field
      for (_ <- 1 to 4) {
        player1Field += deck.dequeue()
        player2Field += deck.dequeue()
      }

      assert(player1Hand.size == 22, s"Player 1 hand size is ${player1Hand.size}, expected 22")
      assert(player2Hand.size == 22, s"Player 2 hand size is ${player2Hand.size}, expected 22")
      assert(player1Field.size == 4, s"Player 1 field size is ${player1Field.size}, expected 4")
      assert(player2Field.size == 4, s"Player 2 field size is ${player2Field.size}, expected 4")
    }
  }

  def gameBeforeStarting(): Unit = {
    println("Waiting for game to start...")
  }

  def getPlayer1Hand: mutable.Queue[Card] = player1Hand

  def getPlayer2Hand: mutable.Queue[Card] = player2Hand

  def getPlayer1Field: ListBuffer[Card] = player1Field

  def getPlayer2Field: ListBuffer[Card] = player2Field

  def fieldPrepare(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]): Try[Unit] = {
    Try {
      playerField.clear()
      val goalkeeper = playerHand.maxBy(card => card.valueToInt(card.value))
      playerHand.dequeueFirst(_ == goalkeeper)
      playerField += goalkeeper
      for (_ <- 1 to 3) {
        val defender = playerHand.dequeue()
        playerField += defender
      }
      // Update player hands to exclude cards that are in the field
      player1Hand = player1Hand.filterNot(card => player1Field.contains(card))
      player2Hand = player2Hand.filterNot(card => player2Field.contains(card))
    }
  }

  def display(): Unit = {
    println("Player 1's hand:")
    println(player1Hand.count(_ => true))
    player1Hand.foreach(println)
    println("\nPlayer 1's field:")
    player1Field.foreach(println)
    println("\nPlayer 2's hand:")
    println(player2Hand.count(_ => true))
    player2Hand.foreach(println)
    println("\nPlayer 2's field:")
    player2Field.foreach(println)
  }

  def refillField(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]): Unit = {
    while (playerField.size < 4 && playerHand.nonEmpty) {
      playerField += playerHand.dequeue()
    }
    val sortedField = playerField.sortBy(card => -card.valueToInt(card.value))
    playerField.clear()
    playerField ++= sortedField
  }
  def playGame(): Unit = {
    
  }
}
