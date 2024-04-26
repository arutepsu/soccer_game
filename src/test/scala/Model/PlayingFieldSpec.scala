package Model

import Model.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class PlayingFieldSpec extends AnyWordSpec with Matchers {

  "A PlayingField" when {
    "initialized" should {
      "have 22 cards in each player's hand" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()
        playingField.getPlayer1Hand.size shouldEqual 22
        playingField.getPlayer2Hand.size shouldEqual 22
      }

      "have 4 cards in each player's field" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()
        playingField.getPlayer1Field.size shouldEqual 4
        playingField.getPlayer2Field.size shouldEqual 4
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
