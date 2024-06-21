package model.PlayingFieldComponent

import model.CardComponent.CardDeck._
import model.CardComponent.CardObject._
import model.CardComponent.{Card, CardDeck}

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Queue}
import scala.util.{Failure, Success, Try}

class GameLogic( val playingField: PlayingField) {

  var currentPlayer: Int = 1

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
              println(s"Attacker scores! $playingField.")
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

  def switchPlayer(): Unit = {
    currentPlayer = if (currentPlayer == 1) 2 else 1
  }

  def getCurrentAttackerCard: Card = {
    val (attackerHand, _, _, _) = getCurrentHandsAndFields()
    attackerHand.head
  }

  def getCurrentHandsAndFields(): (mutable.Queue[Card], mutable.Queue[Card], ListBuffer[Card], ListBuffer[Card]) = {
    currentPlayer match {
      case 1 => (playingField.getPlayer1Hand, playingField.getPlayer2Hand, playingField.getPlayer1Field, playingField.getPlayer2Field)
      case 2 => (playingField.getPlayer2Hand, playingField.getPlayer1Hand, playingField.getPlayer2Field, playingField.getPlayer1Field)
    }
  }

  def displayAttackOptions(): Unit = {
    val (_, _, _, defenderField) = getCurrentHandsAndFields()
    if (defenderField.size == 1) {
      println(s"0 for ${defenderField.head.value} of ${defenderField.head.suit} (goalkeeper)")
    } else {
      defenderField.zipWithIndex.foreach { case (card, index) =>
        if (index > 0) {
          println(s"${index - 1} for ${card.value} of ${card.suit} (defender)")
        }
      }
    }
  }

  def attackAtPosition(position: Int): Try[Boolean] = {
    val (attackerHand, defenderHand, _, defenderField) = getCurrentHandsAndFields()
    val adjustedPosition = if (defenderField.size == 1) 0 else position + 1
    if ((defenderField.size > 1 && position >= 0 && position <= 2 && defenderField.isDefinedAt(adjustedPosition)) ||
      (defenderField.size == 1 && position == 0)) {
      attack(attackerHand, defenderHand, defenderField, adjustedPosition)
    } else {
      Failure(new Exception("Invalid position"))
    }
  }

  def isRoundOver: Boolean = {
    val (_, _, _, defenderField) = getCurrentHandsAndFields()
    defenderField.isEmpty
  }

  def isGameOver: Boolean = {
    val (player1Hand, player2Hand, _, _) = getCurrentHandsAndFields()
    player1Hand.isEmpty || player2Hand.isEmpty
  }
}
