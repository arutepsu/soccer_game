package controller

import scala.swing.event.Event

class GameStarted extends Event
case class GamePlayed(playerName: String) extends Event
class FieldUpdated extends Event
case class NicknameEntered(nickname: String) extends Event