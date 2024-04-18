package Model

import scala.collection.mutable.Queue
import scala.util.Random
import CardObject._
import Suit._

class PlayingField(player1Cards: Queue[Card], player2Cards: Queue[Card]) {
  private var player1Hand: Queue[Card] = Queue.empty
  private var player2Hand: Queue[Card] = Queue.empty
  private var player1Field: Queue[Card] = Queue.empty
  private var player2Field: Queue[Card] = Queue.empty

  // Initialize players' hands and fields

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
  }
}
