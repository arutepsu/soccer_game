package model

import scala.collection.mutable.{Queue, ListBuffer}
import scala.util.{Try, Success, Failure}
import CardObject._
import CardDeck._
import scala.collection.mutable

class PlayingField(private var player1Cards: mutable.Queue[Card], private var player2Cards: mutable.Queue[Card]) {
  var player1Hand: mutable.Queue[Card] = mutable.Queue.empty
  var player2Hand: mutable.Queue[Card] = mutable.Queue.empty
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
      // Ensure deck has 52 cards
      assert(deck.size == 52, s"Deck size is ${deck.size}, expected 52")

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

      // Ensure each player gets 26 cards
      assert(player1Hand.size == 26, s"Player 1 hand size is ${player1Hand.size}, expected 26")
      assert(player2Hand.size == 26, s"Player 2 hand size is ${player2Hand.size}, expected 26")
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
      for (_ <- 1 to 4) { // each player gets 4 cards initially
        val card = playerHand.dequeue()
        playerField += card
      }
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

  def attack(playerCards: mutable.Queue[Card], playerCards2: mutable.Queue[Card], playingFieldD: ListBuffer[Card]): Try[Unit] = {
    Try {
      val playerAttackCard = playerCards.head
      val playerDefenseCard = playingFieldD.head
      val i = playerAttackCard.compare(playerAttackCard.value, playerDefenseCard.value)

      if (i == 1) {
        playingFieldD.remove(0)
        playerCards2 += playerDefenseCard
      } else if (i == -1) {
        playerCards.remove(0)
        playerCards2 += playerAttackCard
      } else {
        playerCards.remove(0)
        playingFieldD.remove(0)
      }
    }
  }

  def drawCard(playerHand: mutable.Queue[Card]): Option[Card] = {
    if (playerHand.nonEmpty) {
      Some(playerHand.dequeue())
    } else {
      None
    }
  }

  def playGame(): Unit = {
    var currentPlayer = 1 // Start with player 1
    var gameOver = false

    while (!gameOver) {
      val (currentHand, currentField, opponentField) = currentPlayer match {
        case 1 => (player1Hand, player1Field, player2Field)
        case 2 => (player2Hand, player2Field, player1Field)
      }
      if (currentHand.isEmpty) {
        println(s"Player $currentPlayer has no cards left in hand.")
        gameOver = true
      } else {
        display()
        println(s"Player $currentPlayer, choose positions to attack (0, 1, 2, or 3, separated by spaces): ")
        val input = scala.io.StdIn.readLine()
        val attackPositionsTry = Try {
          if (input.trim.isEmpty) Array.empty[Int] else input.split(" ").map(_.toInt)
        }

        attackPositionsTry match {
          case Success(attackPositions) =>
            val validAttackPositions = attackPositions.filter(pos => pos >= 0 && pos < opponentField.size)

            validAttackPositions.foreach { attackPosition =>
              val attackingCardOpt = drawCard(currentHand)
              attackingCardOpt match {
                case Some(attackingCard) =>
                  val defendingCard = opponentField(attackPosition)
                  val comparison = attackingCard.compare(attackingCard.value, defendingCard.value)

                  if (comparison >= 1) {
                    println(s"Player $currentPlayer attacks with $attackingCard, defeating $defendingCard.")
                    opponentField.remove(attackPosition)
                    currentField += attackingCard
                  } else if (comparison <= -1) {
                    println(s"Player $currentPlayer attacks with $attackingCard, but $defendingCard defends.")
                    player2Hand.enqueue(defendingCard)
                    opponentField.remove(attackPosition)
                  } else {
                    println(s"Player $currentPlayer attacks with $attackingCard, but $defendingCard has the same value.")
                  }
                case None =>
                  println(s"Player $currentPlayer has no cards left to draw.")
              }
            }
            val blanksOnField = currentField.count(_ == Option)
            for (_ <- 0 until blanksOnField) {
              if (currentHand.nonEmpty) {
                val cardOpt = drawCard(currentHand)
                cardOpt match {
                  case Some(card) => currentField(currentField.indexOf(Option)) = card
                  case None =>
                }
              }
            }
            if (currentPlayer == 1 && validAttackPositions.length == 3 && opponentField.size > 3 && currentHand.isEmpty) {
              val attackingCardOpt = drawCard(currentHand)
              attackingCardOpt match {
                case Some(attackingCard) =>
                  val defendingCard = opponentField(3)
                  val comparison = attackingCard.compare(attackingCard.value, defendingCard.value)
                  if (comparison == 1) {
                    println(s"Player $currentPlayer wins the attack on the fourth card!")
                  }
                case None => //ynothing
              }
            }
            currentPlayer = if (currentPlayer == 1) 2 else 1

            if (player1Hand.isEmpty || player2Hand.isEmpty) {
              gameOver = true
              println("Game Over!")
            }
          case Failure(exception) =>
            println(s"Invalid input: ${exception.getMessage}")
        }
      }
    }
  }
}
