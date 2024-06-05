package util
import model.PlayingField
import model.Card

trait Command {
  def doStep(): Unit
  def undoStep(): Unit
  def redoStep(): Unit
}

/*class DrawCardCommand(playingField: PlayingField, player: Int) extends Command {
  private var card: Option[Card] = None

  override def doStep(): Unit = {
    playingField.gamePrepare()
  }

  override def undoStep(): Unit = {
    playingField.gameBeforeStarting()
  }

  override def redoStep(): Unit = doStep()
}*/