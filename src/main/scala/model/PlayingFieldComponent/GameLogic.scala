package model.PlayingFieldComponent
import model.CardComponent.CardDeck.*
import model.CardComponent.CardObject.*
import model.CardComponent.{Card, CardDeck}
import model.PlayingFieldComponent.PlayingField

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Queue}
import scala.util.{Failure, Success, Try}

class GameLogic(private val playingField: PlayingField) {

  def gameBeforeStarting(): Unit = {
    println("Waiting for game to start...")
  }

  def attack(attackerHand: mutable.Queue[Card], defenderHand: mutable.Queue[Card], defenderField: ListBuffer[Card], position: Int): Try[Boolean] = {
    Try {
      if (attackerHand.isEmpty) {
        println("Attacker has no cards left to draw.")
        Failure(new Exception("No cards left"))
      } else {
        val attackerCard = attackerHand.dequeue()
        if (defenderField.isDefinedAt(position)) {
          val defenderCard = defenderField(position)
          val comparison = attackerCard.compare(attackerCard.value, defenderCard.value)
          if (comparison > 0) {
            println(s"Attacker wins: $attackerCard vs $defenderCard")
            attackerHand.enqueue(attackerCard)
            attackerHand.enqueue(defenderCard)
            defenderField.remove(position)
            if (defenderField.isEmpty) {
              println(s"Attacker scores!$playingField.")
            }
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
            defenderField.remove(position)
            if (defenderHand.nonEmpty) {
              defenderField.insert(position, defenderHand.dequeue())
            }
            Success(false)
          }
        } else {
          throw new Exception("Invalid position.")
        }
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
        case 1 => (playingField.getPlayer1Hand, playingField.getPlayer2Hand, playingField.getPlayer1Field, playingField.getPlayer2Field)
        case 2 => (playingField.getPlayer2Hand, playingField.getPlayer1Hand, playingField.getPlayer2Field, playingField.getPlayer1Field)
      }

      if (attackerHand.isEmpty || defenderHand.isEmpty) {
        println(s"Player $currentPlayer has no cards left in hand.")
        gameOver = true
      } else {
        playingField.display()

        var continueAttacking = true
        while (continueAttacking && attackerHand.nonEmpty) {
          println(s"Player $currentPlayer's turn to attack.")
          println(s"Card to be used for attack: ${attackerHand.head}")

          // Display valid positions to attack
          println("Choose position to attack:")
          if (defenderField.size == 1) {
            println(s"0 for ${defenderField.head.value} of ${defenderField.head.suit} (goalkeeper)")
          } else {
            defenderField.zipWithIndex.foreach { case (card, index) =>
              if (index > 0) {
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

        currentPlayer = if (currentPlayer == 1) 2 else 1
        val (_, newDefenderHand, _, newDefenderField) = currentPlayer match {
          case 1 => (playingField.getPlayer1Hand, playingField.getPlayer2Hand, playingField.getPlayer1Field, playingField.getPlayer2Field)
          case 2 => (playingField.getPlayer2Hand, playingField.getPlayer1Hand, playingField.getPlayer2Field, playingField.getPlayer1Field)
        }
        playingField.refillField(newDefenderHand, newDefenderField)

        if (playingField.getPlayer1Hand.isEmpty || playingField.getPlayer2Hand.isEmpty) {
          gameOver = true
          println("Game Over!")
        }
      }
    }
  }
}
