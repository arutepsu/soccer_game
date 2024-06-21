package aview

import controller._
import model._

import scala.swing.Reactor
import scala.io.StdIn.readLine
import controller.controllerComponent.GameEvents._
import controller.controllerComponent.ControllerBaseImpl.Controller
import model.PlayerComponent.Player
import scala.util.{Try, Success, Failure}

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
      case "start" => startAttack()
      case _ => println("Invalid input. Please enter 'start' to start or 'q' to quit.")
    }
  }

  def startAttack(): Unit = {
    val gameLogic = controller.gamelogic

    println(s"Player ${gameLogic.currentPlayer}'s turn to attack.")
    var continueAttacking = true
    while (continueAttacking && !gameLogic.isGameOver) {
      println(s"Card to be used for attack: ${gameLogic.getCurrentAttackerCard}")
      println("Choose position to attack or enter 'u' to undo, 'r' to redo, 'q' to quit:")
      gameLogic.displayAttackOptions()

      val input = readLine()
      input match {
        case "q" => controller.quit()
        case "u" => controller.undo()
        case "r" => controller.redo()
        case pos => Try(pos.trim.toInt) match {
          case Success(position) =>
            controller.enterAttackPosition(position)
            gameLogic.attackAtPosition(position) match {
              case Success(result) =>
                if (result) {
                  println(s"Attack successful! Continue attacking.")
                } else {
                  println(s"Attack failed or was a draw.")
                  continueAttacking = false
                }
                if (gameLogic.isRoundOver) {
                  println(s"${controller.getCurrentPlayer} wins the round and scores!")
                  continueAttacking = false
                }
              case Failure(exception) =>
                println(s"Attack failed: ${exception.getMessage}")
                continueAttacking = false
            }
          case Failure(exception) =>
            println(s"Invalid input: ${exception.getMessage}")
        }
      }
    }

    if (!gameLogic.isGameOver) {
      gameLogic.switchPlayer()
      controller.publish(new FieldUpdated)
      startAttack()  // Automatically start the attack for the new player
    } else {
      println("Game Over!")
    }
  }

  reactions += {
    case _: GameStarted => println("Game has started!")
    case _: GamePlayed => println("A move has been played!")
    case _: FieldUpdated => controller.gamelogic.playingField.display()
    case NicknamesEntered(nickname1, nickname2) => println(s"Nicknames entered: $nickname1, $nickname2")
    case AttackingPositionEntered(position) => // No need to handle this here
  }

  def printStatus(): Unit = {
    println(controller.getStatusText)
  }
}
