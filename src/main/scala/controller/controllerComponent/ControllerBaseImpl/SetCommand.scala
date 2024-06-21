package controller.controllerComponent.ControllerBaseImpl

import controller.controllerComponent.ControllerBaseImpl.Controller
import util.Command

class SetCommand(controller: Controller) extends Command {
  override def doStep: Unit = controller.startGame()

  override def undoStep: Unit = controller.undo()

  override def redoStep: Unit = controller.redo()
}