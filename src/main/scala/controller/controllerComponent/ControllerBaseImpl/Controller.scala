package controller.controllerComponent.ControllerBaseImpl

import controller.controllerComponent.GameEvents.*
import controller.controllerComponent.GameStatus
import model.CardComponent.Card
import model.PlayingFieldComponent.PlayingField
import util.{Command, UndoManager}

import scala.collection.mutable
import scala.swing.Publisher
import scala.swing.event.Event

class Controller(var playingField: PlayingField) extends Publisher {

  var gameStatus: GameStatus.Value = GameStatus.IDLE
  private val undoManager = new UndoManager
  private var currentPlayer1Name: String = ""
  private var currentPlayer2Name: String = ""
  private var player1Hand: mutable.Queue[Card] = mutable.Queue.empty[Card]
  private var player2Hand: mutable.Queue[Card] = mutable.Queue.empty[Card]

  def startGame(): Unit = {
    playingField.gamePrepare()
    player1Hand = playingField.getPlayer1Hand.take(26)
    player2Hand = playingField.getPlayer2Hand.take(26)
    playingField.fieldPrepare(player1Hand, playingField.getPlayer1Field)
    playingField.fieldPrepare(player2Hand, playingField.getPlayer2Field)
    gameStatus = GameStatus.STARTED
    publish(new GameStarted)
  }

  def playGame(player1Name: String, player2Name: String): Unit = {
    playingField.playGame()
    gameStatus = GameStatus.RUNNING
    publish(GamePlayed(player1Name, player2Name))
  }

  def showMe(): Unit = {
    playingField.display()
    publish(new FieldUpdated)
  }

  def doStep(command: Command): Unit = {
    undoManager.doStep(command)
    publish(new FieldUpdated)
  }

  def undo(): Unit = {
    undoManager.undoStep
    gameStatus = GameStatus.UNDO
    publish(new FieldUpdated)
  }

  def redo(): Unit = {
    undoManager.redoStep
    gameStatus= GameStatus.REDO
    publish(new FieldUpdated)
  }

  def enterNicknames(nickname1: String, nickname2: String): Unit = {
    currentPlayer1Name = nickname1
    currentPlayer2Name = nickname2
    publish(NicknamesEntered(nickname1, nickname2))
  }

  def getCurrentPlayer1Name: String = currentPlayer1Name
  def getCurrentPlayer2Name: String = currentPlayer2Name

  def getStatusText: String = GameStatus.message(gameStatus)

  def getPlayer1Hand: mutable.Queue[Card] = player1Hand

  def getPlayer2Hand: mutable.Queue[Card] = player2Hand

  def getPlayer1Field: mutable.ListBuffer[Card] = playingField.getPlayer1Field
  def getPlayer2Field: mutable.ListBuffer[Card] = playingField.getPlayer2Field
}