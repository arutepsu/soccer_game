package aview

import controller.Controller
import util.Observer
import scala.io.StdIn.readLine
import Model._


class TUI (controller: Controller) extends Observer {
  controller.add(this)
  def displayWelcomeMessage() : Unit = {
    print("Welcome to the Card Game !!!")
  }
  def getUserName: String = {
    println("\nEnter your name")
    readLine()
  }

  def displayFinalStatus(player1 : Player, player2 : Player) : Unit = {
    println(s"\nFinal Status of ${player1.name}:")
    println(player1)
    println(s"\nFinal Status of ${player2.name}:")
    println(player2)
  }
  override def update: Unit =  println()

}
