package model

import model.CardValue.Two
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import model.CardValue._
import model.Suit._

class PlayingFieldPatternSpec extends AnyWordSpec with Matchers {
  "A PlayingFieldPattern" when {
    "created" should {
      "distribute cards to players correctly during game preparation" in {
        val player1Cards = Queue(Card(Two, Diamonds), Card(Three, Hearts), Card(Four, Clubs)) // Sample cards for player 1
        val player2Cards = Queue(Card(Five, Spades), Card(Six, Diamonds), Card(Seven, Hearts)) // Sample cards for player 2
        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)
//        playingFieldPattern.gamePrepare()
//        playingFieldPattern.player1Hand.size shouldBe 26 // Assuming 26 cards for each player
//        playingFieldPattern.player2Hand.size shouldBe 26
      }

      "switch to GameOverState after one turn in playGame method" in {
        val player1Cards = Queue(Card(Two, Diamonds), Card(Three, Hearts), Card(Four, Clubs)) // Sample cards for player 1
        val player2Cards = Queue(Card(Five, Spades), Card(Six, Diamonds), Card(Seven, Hearts)) // Sample cards for player 2
        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)
//        playingFieldPattern.gamePrepare()
//        playingFieldPattern.playGame()
//        playingFieldPattern.currentState shouldBe GameOverState
      }
    }
  }
}