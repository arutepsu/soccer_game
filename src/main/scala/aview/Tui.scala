package aview

import controller._
import model._
import scala.swing.Reactor
import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def displayWelcomeMessage(): Unit = {
    println("Welcome to the Soccer Card Game !!!")
  }

  def getUserName: String = {
    println("\nEnter your name:")
    readLine()
  }

  def displayFinalStatus(player1: Player, player2: Player): Unit = {
    println(s"\nFinal Status of ${player1.name}:")
    println(player1)

    println(s"\nFinal Status of ${player2.name}:")
    println(player2)
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "show" => controller.showMe()
      case "p" => controller.playGame("PlayerName") // Replace "PlayerName" with actual player name if needed
      case "s" =>
        val playerName = getUserName
        controller.startGame()
        controller.enterNickname(playerName)
      case "u" => controller.undo()
      case "r" => controller.redo()
      case "d" => controller.doStep()
      case name: String => controller.enterNickname(name) // To handle name input
    }
  }

  reactions += {
    case _: GameStarted => println("Game has started!")
    case _: GamePlayed => println("A move has been played!")
    case _: FieldUpdated => println("The field has been updated!")
    case NicknameEntered(nickname) => println(s"Nickname entered: $nickname")
  }

  def printStatus: Unit = {
    println(controller.getStatusText)
  }
}
