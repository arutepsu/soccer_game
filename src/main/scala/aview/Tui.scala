package aview

import controller.Controller
import util.Observer
import scala.io.StdIn.readLine
import model._

class Tui(controller: Controller) extends Observer {
  controller.add(this)

  def displayWelcomeMessage(): Unit = {
    println("Welcome to the Card Game !!!")
  }

  def getUserName: String = {
    println("\nEnter your name")
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
      case "p" => controller.playGame()
      case "s" => controller.startGame()
      case "u" => controller.undo()
      case "r" => controller.redo()
      case "d" => controller.doStep()
    }
  }
  override def update: Boolean = {
    true
  }
}
