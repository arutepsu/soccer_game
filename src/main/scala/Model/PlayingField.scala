package Model

import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import CardObject._
import CardDeck._
import scala.io.StdIn
import scala.collection.mutable
import Player._

class PlayingField(player1Cards: mutable.Queue[Card], player2Cards: mutable.Queue[Card]) {
  private val player1Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player2Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player1Field: ListBuffer[Card] = ListBuffer.empty
  private val player2Field: ListBuffer[Card] = ListBuffer.empty
  def gamePrepare() : Unit = {
    val deck = CardDeck.createStandardDeck()
    CardDeck.shuffleDeck(deck)
    // Distribute cards to player 1
    for (_ <- 1 to 26) { // Assuming each player gets 26 cards initially
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
  def getPlayer1Field: ListBuffer[Card] = player1Field
  def getPlayer2Field: ListBuffer[Card] = player2Field



  def fieldPrepare(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]) : Unit = {
    for (_ <- 1 to 4) { //  each player gets 4 cards initially
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

  def attack(playerCards: mutable.Queue[Card], playerCards2: mutable.Queue[Card], playingFieldD: ListBuffer[Card]): Unit = {
    val playerAttackCard = playerCards.head
    val playerDefenseCard = playingFieldD.head
    val i = playerAttackCard.compare(playerAttackCard.value, playerDefenseCard.value)

    if (i == 1) {
      playingFieldD.remove(0)
      playerCards2 += playerDefenseCard
    } else if (i == -1) {
      playerCards.remove(0)
      playerCards2 += playerAttackCard
    } else { // Handle case when i == 0
      playerCards.remove(0)
      playingFieldD.remove(0)
    }
  }

  def playGame(): Unit = {
    var currentPlayer = 1 // Start with player 1
    var gameOver = false

    while (!gameOver) {
      // Determine the current player's hand, field, and opponent's field
      val (currentHand, currentField, opponentField) = currentPlayer match {
        case 1 => (player1Hand, player1Field, player2Field)
        case 2 => (player2Hand, player2Field, player1Field)
      }
      // Check if the current player has cards left in hand
      if (currentHand.isEmpty) {
        println(s"Player $currentPlayer has no cards left in hand.")
        gameOver = true
      } else {
        // Display current game state
        display()

        // Player chooses positions to attack
        println(s"Player $currentPlayer, choose positions to attack (0, 1, 2, or 3, separated by spaces): ")
        val input = StdIn.readLine()
        val attackPositions = if (input.trim.isEmpty) Array.empty[Int] else input.split(" ").map(_.toInt)

        // Ensure the attack positions are valid
        val validAttackPositions = attackPositions.filter(pos => pos >= 0 && pos < opponentField.size)

        // Perform attacks
        validAttackPositions.foreach { attackPosition =>
          val attackingCard = currentHand.head
          val defendingCard = opponentField(attackPosition)
          val comparison = attackingCard.compare(attackingCard.value, defendingCard.value)

          if (comparison >= 1) {
            println(s"Player $currentPlayer attacks with $attackingCard, defeating $defendingCard.")
            opponentField.remove(attackPosition)
            currentHand.dequeue()
            currentField += attackingCard
          } else if (comparison <= -1) {
              println(s"Player $currentPlayer attacks with $attackingCard, but $defendingCard defends.")
              currentHand.dequeue()
              player2Hand.enqueue(defendingCard)
              opponentField.remove(attackPosition)
          } else if (comparison == 0) {
            println(s"Player $currentPlayer attacks with $attackingCard, but $defendingCard has the same value.")
          }
        }
        // Fill blank positions on the field
        val blanksOnField = currentField.count(_ == null)
        for (_ <- 0 until blanksOnField) {
          if (currentHand.nonEmpty) {
            val card = currentHand.dequeue()
            currentField(currentField.indexOf(null)) = card
          }
        }
        // Check if Player 1 won the attack on the fourth card
        if (currentPlayer == 1 && validAttackPositions.length == 3 && opponentField.size > 3 && currentHand.isEmpty) {
          val attackingCard = currentHand.head
          val defendingCard = opponentField(3) // Attack the fourth card
          val comparison = attackingCard.compare(attackingCard.value, defendingCard.value)
          if (comparison == 1) {
            println(s"Player $currentPlayer wins the attack on the fourth card!")
            // Increment Player 1's score
            // Implement your scoring mechanism here
          }
        }
        // Switch to the other player
        currentPlayer = if (currentPlayer == 1) 2 else 1

        // Check if any player has run out of cards
        if (player1Hand.isEmpty || player2Hand.isEmpty) {
          gameOver = true
          println("Game Over!")
        }
      }
    }
  }
}

