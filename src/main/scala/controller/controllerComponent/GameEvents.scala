package controller.controllerComponent

import scala.swing.event.Event

object GameEvents {
  class GameStarted extends Event
  case class GamePlayed(player1Name: String, player2Name: String) extends Event
  class FieldUpdated extends Event
  case class NicknamesEntered(nickname1: String, nickname2: String) extends Event
  case class AttackingPositionEntered(int: Int) extends Event
  class GameFinished extends Event
}
