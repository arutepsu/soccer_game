package model

import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import scala.io.StdIn

class PlayingFieldPattern(player1Cards: mutable.Queue[Card], player2Cards: mutable.Queue[Card]) {
  private val player1Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player2Hand: mutable.Queue[Card] = mutable.Queue.empty
  private val player1Field: ListBuffer[Card] = ListBuffer.empty
  private val player2Field: ListBuffer[Card] = ListBuffer.empty

  // State pattern:
  private var currentState: PlayingFieldState = PlayerTurnState

  // Strategy pattern:
  private var comparisonStrategy: ComparisonStrategy = CardComparisonStrategies.StandardComparison

  // Factory pattern:
  def setComparisonStrategy(strategyType: String): Unit = {
    comparisonStrategy = ComparisonStrategyFactory.createStrategy(strategyType)
  }

  // Game preparation method
  def gamePrepare(): Unit = {
    // Distribute cards to player 1
    for (_ <- 1 to 26) {
      val card = player1Cards.dequeue()
      player1Hand.enqueue(card)
    }
    // Distribute cards to player 2
    for (_ <- 1 to 26) {
      val card = player2Cards.dequeue()
      player2Hand.enqueue(card)
    }
  }

  // Method to handle player turns and game logic
  def playGame(): Unit = {
    var gameOver = false

    while (!gameOver) {
      currentState match {
        case PlayerTurnState =>
          currentState.handle(player1Hand, player2Hand, player1Field, player2Field)
          // Switch to the other player
          currentState = GameOverState // switch to GameOverState after one turn
        case GameOverState =>
          currentState.handle(player1Hand, player2Hand, player1Field, player2Field)
          gameOver = true
      }
    }
  }
}