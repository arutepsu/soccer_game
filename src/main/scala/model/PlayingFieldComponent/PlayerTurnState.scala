package model.PlayingFieldComponent

import model.CardComponent.Card

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Queue}
import scala.io.StdIn

object PlayerTurnState extends PlayingFieldState {
  override def handle(player1Hand: mutable.Queue[Card], player2Hand: mutable.Queue[Card], player1Field: ListBuffer[Card], player2Field: ListBuffer[Card]): Unit = {
    // Display current game
    display(player1Hand, player1Field, player2Hand, player2Field)

    // Player chooses positions to attack
    println("Player 1, choose positions to attack (0, 1, 2, or 3, separated by spaces): ")
    val player1Input = StdIn.readLine()
    val player1AttackPositions = if (player1Input.trim.isEmpty) Array.empty[Int] else player1Input.split(" ").map(_.toInt)

    // Perform attacks for player 1
    performAttacks(player1Hand, player1Field, player2Field, player1AttackPositions)

    // Fill blank positions on player 1 field
    fillBlanks(player1Hand, player1Field)

    // Check if Player 1 won the attack on the fourth card
    checkFourthCardAttack(player1Hand, player1Field, player2Field)
  }

   def display(player1Hand: mutable.Queue[Card], player1Field: ListBuffer[Card], player2Hand: mutable.Queue[Card], player2Field: ListBuffer[Card]): Unit = {
    println("Player 1's hand:")
    player1Hand.foreach(println)
    println("\nPlayer 1's field:")
    player1Field.foreach(println)
    println("\nPlayer 2's hand:")
    player2Hand.foreach(println)
    println("\nPlayer 2's field:")
    player2Field.foreach(println)
  }

  private def performAttacks(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card], opponentField: ListBuffer[Card], attackPositions: Array[Int]): Unit = {
  }

  private def fillBlanks(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card]): Unit = {
  }

  private def checkFourthCardAttack(playerHand: mutable.Queue[Card], playerField: ListBuffer[Card], opponentField: ListBuffer[Card]): Unit = {
  }
}
