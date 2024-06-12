package controller
import util.Command

class SetCommand(controller: Controller) extends Command {
  override def doStep: Unit = controller.startGame()

  override def undoStep: Unit = controller.playingField.gameBeforeStarting()

  override def redoStep: Unit = controller.startGame()
}