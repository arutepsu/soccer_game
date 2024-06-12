package model

import model.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class PlayingFieldSpec extends AnyWordSpec with Matchers {

  "A PlayingField" when {
    "initialized" should {
      "have 26 cards in each player's hand" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()
        playingField.getPlayer1Hand.size shouldEqual 26
        playingField.getPlayer2Hand.size shouldEqual 26
      }

      "have 0 cards in each player's field" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()
        playingField.getPlayer1Field.size shouldEqual 0
        playingField.getPlayer2Field.size shouldEqual 0
      }
    }

    "playing the game" should {
      "prepare the game and display initial state" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.playGame()
      }
    }
  }
}