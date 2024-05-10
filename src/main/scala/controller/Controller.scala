package controller
import model.{Card, PlayingField}
import util.Observable

import scala.collection.mutable
import model.CardDeck.*

class Controller(var playingField: PlayingField) extends Observable {

//  private val playingField = new PlayingField(
//    player1Cards = scala.collection.mutable.Queue.empty,
//    player2Cards = scala.collection.mutable.Queue.empty )
  def startGame() : Unit = {
    playingField.gamePrepare()
    playingField.fieldPrepare(playingField.getPlayer1Hand, playingField.getPlayer1Field)
    playingField.fieldPrepare(playingField.getPlayer2Hand, playingField.getPlayer2Field)
    playingField.playGame()
    notifyObservers
  }
}