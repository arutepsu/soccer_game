package controller.controllerComponent.ControllerBaseImpl

import model.CardComponent.Card
import util.Command

class StepCommand(controller: Controller, winner: Card, loser: Card) extends Command {
  override def doStep: Unit = {
    controller.playGame(controller.getCurrentPlayer1Name, controller.getCurrentPlayer2Name)
  }

  override def undoStep: Unit = {

  }

  override def redoStep: Unit = doStep
}
