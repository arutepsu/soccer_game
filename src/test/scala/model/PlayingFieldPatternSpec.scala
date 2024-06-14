package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.mutable.Queue
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import model.Suit._
import model.CardValue._

class PlayingFieldPatternSpec extends AnyWordSpec with Matchers {
  "A PlayingFieldPattern" when {
    "created" should {
      "initialize with empty hands and fields" in {
        val player1Cards = mutable.Queue.empty[Card]
        val player2Cards = mutable.Queue.empty[Card]
        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)

        playingFieldPattern.player1Hand shouldBe empty
        playingFieldPattern.player2Hand shouldBe empty
        playingFieldPattern.player1Field shouldBe empty
        playingFieldPattern.player2Field shouldBe empty
      }

      "set comparison strategy correctly" in {
        val player1Cards = mutable.Queue.empty[Card]
        val player2Cards = mutable.Queue.empty[Card]
        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)

        playingFieldPattern.setComparisonStrategy("standard")
        playingFieldPattern.comparisonStrategy shouldBe CardComparisonStrategies.StandardComparison
      }

      "distribute cards to players correctly during game preparation" in {
        val player1Cards = mutable.Queue.fill(26)(Card(Two, Diamonds))
        val player2Cards = mutable.Queue.fill(26)(Card(Five, Spades))
        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)

        playingFieldPattern.gamePrepare()

        playingFieldPattern.player1Hand.size shouldBe 26
        playingFieldPattern.player2Hand.size shouldBe 26
      }

//      "switch to GameOverState after one turn in playGame method" in {
//        val player1Cards = mutable.Queue.fill(26)(Card(Two, Diamonds))
//        val player2Cards = mutable.Queue.fill(26)(Card(Five, Spades))
//        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)
//
//        playingFieldPattern.gamePrepare()
//        playingFieldPattern.playGame()
//
//        playingFieldPattern.currentState shouldBe GameOverState
//      }

//      "handle game logic and player turns correctly" in {
//        val player1Cards = mutable.Queue(Card(Two, Diamonds), Card(Three, Hearts), Card(Four, Clubs))
//        val player2Cards = mutable.Queue(Card(Five, Spades), Card(Six, Diamonds), Card(Seven, Hearts))
//        val playingFieldPattern = new PlayingFieldPattern(player1Cards, player2Cards)
//
//        playingFieldPattern.gamePrepare()
//        playingFieldPattern.playGame()
//
//        playingFieldPattern.currentState shouldBe GameOverState
//      }
    }
  }
}
