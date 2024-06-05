package controller

import model.{PlayingField, Card}
import util.{Observable, UndoManager, Command}
import controller.GameStatus._
import scala.collection.mutable

class Controller(var playingField: PlayingField) extends Observable {

  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager

  def startGame(): Unit = {
    playingField.gamePrepare()
    playingField.fieldPrepare(playingField.getPlayer1Hand, playingField.getPlayer1Field)
    playingField.fieldPrepare(playingField.getPlayer2Hand, playingField.getPlayer2Field)
    notifyObservers
  }

  def playGame(): Unit = {
    playingField.playGame()
  }

  def showMe(): Unit = {
    playingField.display()
  }

    def doStep(): Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers
  }
}
