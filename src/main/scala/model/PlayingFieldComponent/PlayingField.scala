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

  def attack(attackerHand: mutable.Queue[Card], defenderHand: mutable.Queue[Card], defenderField: ListBuffer[Card], position: Int): Try[Boolean] = {
    Try {
      if (attackerHand.isEmpty) {
        println("Attacker has no cards left to draw.")
        Failure(new Exception("No cards left"))
      }
      val attackerCard = attackerHand.dequeue()
      if (defenderField.isDefinedAt(position)) {
        val defenderCard = defenderField(position)
        val comparison = attackerCard.compare(attackerCard.value, defenderCard.value)
        if (comparison > 0) {
          println(s"Attacker wins: $attackerCard vs $defenderCard")
          attackerHand.enqueue(attackerCard)
          attackerHand.enqueue(defenderCard)
          defenderField.remove(position)
          Success(true)
        } else if (comparison < 0) {
          println(s"Defender wins: $attackerCard vs $defenderCard")
          defenderHand.enqueue(attackerCard)
          defenderHand.enqueue(defenderCard)
          defenderField.remove(position)
          if (defenderHand.nonEmpty) {
            defenderField.insert(position, defenderHand.dequeue())
          }
          Success(false)
        } else {
          println(s"Draw: $attackerCard vs $defenderCard")
          attackerHand.enqueue(attackerCard)
          defenderHand.enqueue(defenderCard)
          Success(false)
        }
      } else {
        throw new Exception("Invalid position.")
      }
    }.flatten
  }

  def drawCard(playerHand: mutable.Queue[Card]): Option[Card] = {
    if (playerHand.nonEmpty) {
      Some(playerHand.dequeue())
    } else {
      None
    }
  }

  def playGame(): Unit = {
    var currentPlayer = 1
    var gameOver = false

    while (!gameOver) {
      val (attackerHand, defenderHand, attackerField, defenderField) = currentPlayer match {
        case 1 => (player1Hand, player2Hand, player1Field, player2Field)
        case 2 => (player2Hand, player1Hand, player2Field, player1Field)
      }

      if (attackerHand.isEmpty || defenderHand.isEmpty) {
        println(s"Player $currentPlayer has no cards left in hand.")
        gameOver = true
      } else {
        display()

        var continueAttacking = true
        while (continueAttacking && attackerHand.nonEmpty) {
          println(s"Player $currentPlayer's turn to attack.")
          println(s"Card to be used for attack: ${attackerHand.head}")

          // Display valid positions to attack
          println("Choose position to attack:")
          if (defenderField.size == 1) {
            // Show goalkeeper if all defenders are defeated
            println(s"0 for ${defenderField.head.value} of ${defenderField.head.suit} (goalkeeper)")
          } else {
            // Show defenders only
            defenderField.zipWithIndex.foreach { case (card, index) =>
              if (index > 0) { // Skip the goalkeeper
                println(s"${index - 1} for ${card.value} of ${card.suit} (defender)")
              }
            }
          }

          val input = scala.io.StdIn.readLine()
          val attackPositionTry = Try(input.trim.toInt)

          attackPositionTry match {
            case Success(position) =>
              val adjustedPosition = if (defenderField.size == 1) 0 else position + 1
              if ((defenderField.size > 1 && position >= 0 && position <= 2 && defenderField.isDefinedAt(adjustedPosition)) ||
                (defenderField.size == 1 && position == 0)) {
                attack(attackerHand, defenderHand, defenderField, adjustedPosition) match {
                  case Success(result) =>
                    continueAttacking = result
                    if (defenderField.isEmpty) {
                      println(s"Player $currentPlayer wins the round and scores!")
                    }
                  case Failure(exception) =>
                    println(s"Attack failed: ${exception.getMessage}")
                    continueAttacking = false
                }
              } else {
                println("Invalid position. Try again.")
              }

            case Failure(exception) =>
              println(s"Invalid input: ${exception.getMessage}")
          }
        }

        // Switch players
        currentPlayer = if (currentPlayer == 1) 2 else 1
//----------------------------------------------------
        // Refill the new defender's field
        val (_, newDefenderHand, _, newDefenderField) = currentPlayer match {
          case 1 => (player1Hand, player2Hand, player1Field, player2Field)
          case 2 => (player2Hand, player1Hand, player2Field, player1Field)
        }
        //________________
        refillField(newDefenderHand, newDefenderField)

        if (player1Hand.isEmpty || player2Hand.isEmpty) {
          gameOver = true
          println("Game Over!")
        }
      }
    }
  }

  private def refillField(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]): Unit = {
    while (playerField.size < 4 && playerHand.nonEmpty) {
      playerField += playerHand.dequeue()
    }
    // Sort the field to ensure the goalkeeper (index 0) is the strongest card
    val sortedField = playerField.sortBy(card => -card.valueToInt(card.value))
    playerField.clear()
    playerField ++= sortedField
  }

}