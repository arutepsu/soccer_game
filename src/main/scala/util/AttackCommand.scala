package util

import model.CardComponent.Card
import model.PlayingFieldComponent.PlayingField
import scala.collection.mutable
import model.PlayingFieldComponent.GameLogic

class AttackCommand(playingField: PlayingField, gameLogic: GameLogic, attackerHand: mutable.Queue[Card], defenderHand: mutable.Queue[Card], defenderField: mutable.ListBuffer[Card], position: Int) extends Command {
  var attackerCard: Option[Card] = None
  var defenderCard: Option[Card] = None
  var result: Option[Boolean] = None

  override def doStep: Unit = {
    result = gameLogic.attack(attackerHand, defenderHand, defenderField, position).toOption
    if (result.get) {
      attackerCard = Some(attackerHand.dequeue())
      defenderCard = defenderField.lift(position)
      defenderField.remove(position)
    }
  }

  override def undoStep: Unit = {
    result match {
      case Some(true) =>
        defenderCard.foreach(defenderHand.enqueue(_))
        attackerCard.foreach(attackerHand.enqueue(_))
      case Some(false) =>
        attackerCard.foreach(attackerHand.enqueue(_))
      case _ =>
    }
  }

  override def redoStep: Unit = {
    doStep
  }
}
