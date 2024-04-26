package Model

import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {
    println("Welcome to the Card Game!")

    // Prompt the user to input their username
    def username = readLine("Enter your username: ")

    // Create players
    val player1 = Player(username, List.empty)
    val player2 = Player("Computer", List.empty) // Assuming player 2 is always the computer

    // Create playing field
    val playingField = new PlayingField(
      player1Cards = scala.collection.mutable.Queue.empty,
      player2Cards = scala.collection.mutable.Queue.empty
    )

    // Start the game
    playingField.playGame()
  }
}

