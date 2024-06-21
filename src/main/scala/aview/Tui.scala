package aview

import controller.*
import model.*

import scala.swing.Reactor
import scala.io.StdIn.readLine
import controller.controllerComponent.GameEvents.*
import controller.controllerComponent.ControllerBaseImpl.Controller
import model.PlayerComponent.Player

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def displayWelcomeMessage(): Unit = {
    println("Welcome to the Soccer Card Game !!!")
  }

  def getUser1Name: String = {
    println("\nEnter Player1 name:")
    readLine()
  }

  def getUser2Name: String = {
    println("\nEnter Player2 name:")
    readLine()
  }

  def displayFinalStatus(player1: Player, player2: Player): Unit = {
    println(s"\nFinal Status of ${player1.name}:")
    println(player1)

    println(s"\nFinal Status of ${player2.name}:")
    println(player2)
  }

  def processInputLine(input: String, currentPlayer: String): Unit = {
    input match {
      case "q" => controller.quit()
      case "a" => controller.playGame(controller.getCurrentPlayer1Name, controller.getCurrentPlayer2Name)
      case "u" => controller.undo()
      case "r" => controller.redo()
      case _ => println("Invalid input. Please enter 'a' to attack, 'r' to redo, 'u' to undo, or 'q' to quit.")
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