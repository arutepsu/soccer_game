
import Model.*
import aview.TUI

import scala.collection.mutable
import scala.io.StdIn
import controller.Controller
/*object Main {
  def main(args: Array[String]): Unit = {
    println("Welcome to the Card Game!")

    //    val username = scala.io.StdIn.readLine()
    print("Enter your username: ")
    var username = ""
    username = StdIn.readLine()

    val player1 = Player(username, List.empty)

    val player2 = Player("Computer", List.empty)
    println(s"Player 2: ${player2.name}")

    val player1Cards = mutable.Queue.empty[Card]
    val player2Cards = mutable.Queue.empty[Card]

    val playingField = new PlayingField(player1Cards, player2Cards)

    playingField.gamePrepare()
    playingField.fieldPrepare(playingField.getPlayer1Hand, playingField.getPlayer1Field)
    playingField.fieldPrepare(playingField.getPlayer1Hand, playingField.getPlayer2Field)
    playingField.playGame()
  }
}*/


object Main {
  val Controller = new Controller()
  val Tui = new TUI(Controller)
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