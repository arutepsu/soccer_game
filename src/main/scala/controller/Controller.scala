package controller

import model.{Card, PlayingField}
import util.{Command, UndoManager}

import scala.collection.mutable
import scala.swing.Publisher
import scala.swing.event.Event

class Controller(var playingField: PlayingField) extends Publisher {

  var gameStatus: GameStatus.Value = GameStatus.IDLE
  private val undoManager = new UndoManager
  private var currentPlayerName: String = ""
  private var player1Hand: mutable.Queue[Card] = mutable.Queue.empty[Card]
  private var player2Hand: mutable.Queue[Card] = mutable.Queue.empty[Card]

  def startGame(): Unit = {
    playingField.gamePrepare()
    player1Hand = playingField.getPlayer1Hand.take(26)
    player2Hand = playingField.getPlayer2Hand.take(26)
    playingField.fieldPrepare(player1Hand, playingField.getPlayer1Field)
    playingField.fieldPrepare(player2Hand, playingField.getPlayer2Field)
    gameStatus = GameStatus.NOT_FINISH
    publish(new GameStarted)
  }

  def playGame(playerName: String): Unit = {
    playingField.playGame()
    publish(GamePlayed(playerName))
  }

  def showMe(): Unit = {
    playingField.display()
    publish(new FieldUpdated)
  }

  def doStep(): Unit = {
    undoManager.undoStep
    publish(new FieldUpdated)
  }

  def undo(): Unit = {
    undoManager.undoStep
    publish(new FieldUpdated)
  }

  def redo(): Unit = {
    undoManager.redoStep
    publish(new FieldUpdated)
  }

  def enterNickname(nickname: String): Unit = {
    currentPlayerName = nickname
    publish(NicknameEntered(nickname))
  }

  def getCurrentPlayerName: String = currentPlayerName

  def getStatusText: String = GameStatus.message(gameStatus)

  def getPlayer1Hand: mutable.Queue[Card] = player1Hand

  def getPlayer2Hand: mutable.Queue[Card] = player2Hand

  def getPlayer1Field: mutable.ListBuffer[Card] = playingField.getPlayer1Field
  def getPlayer2Field: mutable.ListBuffer[Card] = playingField.getPlayer2Field
}
