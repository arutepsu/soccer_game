//package Model
//
//object Main {
//  def main(args: Array[String]): Unit = {
//    println("Welcome to the Card Game!")
//
//    print("Enter your username: ")
//    def username = scala.io.StdIn.readLine()
//
//    val player1 = Player(username, List.empty)
//    println(player1.name)
//    val player2 = Player("Computer", List.empty)
//
//    val playingField = new PlayingField(
//      player1Cards = scala.collection.mutable.Queue.empty,
//      player2Cards = scala.collection.mutable.Queue.empty
//    )
//
//    // Start the game
//    playingField.playGame()
//
//    println(player1)
//    println(player2)
//  }
//}
import Model.*

import scala.io.StdIn
object Main {
  def main(args: Array[String]): Unit = {
    println("Welcome to the Card Game!")

    //    val username = scala.io.StdIn.readLine()
    print("Enter your username: ")
    var username = ""
    username = StdIn.readLine("What is your name?")

    val player1 = Player(username, List.empty)

    val player2 = Player("Computer", List.empty)
    println(s"Player 2: ${player2.name}")

    val playingField = new PlayingField(
      player1Cards = scala.collection.mutable.Queue.empty,
      player2Cards = scala.collection.mutable.Queue.empty
    )

    // Start the game
    playingField.playGame()
    println(s"\nFinal status of ${player1.name}:")
    println(player1)
    println(s"\nFinal status of ${player2.name}:")
    println(player2)

  }
}