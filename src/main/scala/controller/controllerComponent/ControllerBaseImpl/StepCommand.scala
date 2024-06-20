package controller.controllerComponent.ControllerBaseImpl

import controller.controllerComponent.ControllerBaseImpl.Controller
import util.Command

class StepCommand(controller: Controller) extends Command {
  override def doStep: Unit = {
    // Define the action that should happen when the step is executed.
    controller.playGame(controller.getCurrentPlayer1Name, controller.getCurrentPlayer2Name)
  }

  override def undoStep: Unit = {
    // Define the undo logic for this step, if applicable.
  }

  override def redoStep: Unit = doStep
}
