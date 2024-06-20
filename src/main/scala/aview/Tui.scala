package aview

import controller._
import model._
import scala.swing.Reactor
import scala.io.StdIn.readLine
import controller.GameEvents._

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def displayWelcomeMessage(): Unit = {
    println("Welcome to the Soccer Card Game !!!")
  }

  private def getUser1Name: String = {
    println("\nEnter Player1 name:")
    readLine()
  }

  private def getUser2Name: String = {
    println("\nEnter Player2 name:")
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
      case "p" => controller.playGame(controller.getCurrentPlayer1Name, controller.getCurrentPlayer2Name)
      case "s" =>
        val player1Name = getUser1Name
        val player2Name = getUser2Name
        controller.enterNicknames(player1Name, player2Name)
        controller.startGame()
      case "u" => controller.undo()
      case "r" => controller.redo()
      case "d" => controller.doStep()
    }
  }

  reactions += {
    case _: GameStarted => println("Game has started!")
    case _: GamePlayed => println("A move has been played!")
    case _: FieldUpdated => println("The field has been updated!")
    case NicknamesEntered(nickname1, nickname2) => println(s"Nicknames entered: $nickname1, $nickname2")
  }

  def printStatus(): Unit = {
    println(controller.getStatusText)
  }
}
