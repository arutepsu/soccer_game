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
import model.*

import scala.io.StdIn
import aview.Tui
import controller.Controller
object Main {
  val Controller = new Controller(
    new PlayingField(
    player1Cards = scala.collection.mutable.Queue.empty,
    player2Cards = scala.collection.mutable.Queue.empty
    )
  )
  val Tui = new Tui(Controller)
  Controller.notifyObservers
  def main(args: Array[String]): Unit = {

    Tui.displayWelcomeMessage()

    val username = Tui.getUserName
    val player1 = Player(username, List.empty)
    val player2 = Player("CPU", List.empty)

    Controller.startGame()
    Tui.displayFinalStatus(player1,player1)
  }
}