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
import model._
import scala.io.StdIn
import aview._
import aview.gui._
import controller.Controller

object Main {
  val controller = new Controller(
    new PlayingField(
      player1Cards = scala.collection.mutable.Queue.empty,
      player2Cards = scala.collection.mutable.Queue.empty
    )
  )

  val tui = new Tui(controller)
  val gameGui = new GameGui(controller)
  gameGui.visible = true

  def main(args: Array[String]): Unit = {
    tui.displayWelcomeMessage()

    val username = tui.getUserName
    val player1 = Player(username, List.empty)
    val player2 = Player("CPU", List.empty)

    controller.startGame()
    controller.enterNickname(username)

    var input = ""
    while (input != "q"){
      input = StdIn.readLine()
      tui.processInputLine(input)
    }

    tui.displayFinalStatus(player1, player2)
  }
}
