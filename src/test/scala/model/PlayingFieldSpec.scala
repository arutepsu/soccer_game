package model

import model.*
import model.CardComponent.{Card, CardValue, Suit}
import model.PlayingFieldComponent.PlayingField
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

    "preparing the field" should {
      "distribute 4 cards to each player's field from their hand" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()

        playingField.fieldPrepare(playingField.getPlayer1Hand, playingField.getPlayer1Field)
        playingField.fieldPrepare(playingField.getPlayer2Hand, playingField.getPlayer2Field)

        playingField.getPlayer1Field.size shouldEqual 4
        playingField.getPlayer2Field.size shouldEqual 4
        playingField.getPlayer1Hand.size shouldEqual 22
        playingField.getPlayer2Hand.size shouldEqual 22
      }
    }

    "during gameplay" should {
      "allow players to attack and update the field correctly" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()

        playingField.fieldPrepare(playingField.getPlayer1Hand, playingField.getPlayer1Field)
        playingField.fieldPrepare(playingField.getPlayer2Hand, playingField.getPlayer2Field)

        // Simulate an attack
        val initialPlayer2FieldSize = playingField.getPlayer2Field.size
        playingField.attack(playingField.getPlayer1Hand, playingField.getPlayer2Hand, playingField.getPlayer2Field)

        // Check if the field or hands have been updated correctly
        assert(playingField.getPlayer2Field.size < initialPlayer2FieldSize || playingField.getPlayer1Hand.size < 22 || playingField.getPlayer2Hand.size > 22)
      }

      "end the game when a player has no cards left in hand" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()

        // Empty one player's hand to simulate end of game
        playingField.getPlayer1Hand.dequeueAll(_ => true)

        // Check if the game ends
        noException should be thrownBy playingField.playGame()
      }
    }



    "displaying the playing field" should {
      "print the current state of the game" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()
        noException should be thrownBy playingField.display()
      }
    }

//    "setting player cards" should {
//      "correctly set player 1 and player 2 cards" in {
//        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
//        val newPlayer1Cards = mutable.Queue[Card](Card(CardValue.Two, Suit.Hearts), Card(CardValue.Three, Suit.Diamonds))
//        val newPlayer2Cards = mutable.Queue[Card](Card(CardValue.Four, Suit.Spades), Card(CardValue.Five, Suit.Clubs))
//
//        playingField.setPlayer1Cards(newPlayer1Cards)
//        playingField.setPlayer2Cards(newPlayer2Cards)
//
//        playingField.getPlayer1Hand shouldEqual newPlayer1Cards
//        playingField.getPlayer2Hand shouldEqual newPlayer2Cards
//      }
//    }
//
//    "handling attack logic" should {
//      "properly process a winning attack" in {
//        val player1Hand = mutable.Queue[Card](Card(CardValue.Two, Suit.Hearts))
//        val player2Hand = mutable.Queue[Card]()
//        val player2Field = ListBuffer[Card](Card(CardValue.Four, Suit.Spades))
//
//        val playingField = new PlayingField(player1Hand, player2Hand)
//        playingField.attack(player1Hand, player2Hand, player2Field)
//
//        player2Field shouldBe empty
//        player2Hand should contain(Card(CardValue.Four, Suit.Spades))
//      }
//
//      "properly process a losing attack" in {
//        val player1Hand = mutable.Queue[Card](Card(CardValue.Two, Suit.Hearts))
//        val player2Hand = mutable.Queue[Card]()
//        val player2Field = ListBuffer[Card](Card(CardValue.Four, Suit.Spades))
//
//        val playingField = new PlayingField(player1Hand, player2Hand)
//        playingField.attack(player1Hand, player2Hand, player2Field)
//
//        player1Hand shouldBe empty
//        player2Hand should contain(Card(CardValue.Two, Suit.Hearts))
//      }

      "properly process a tie attack" in {
        val player1Hand = mutable.Queue[Card](Card(CardValue.Two, Suit.Hearts))
        val player2Hand = mutable.Queue[Card]()
        val player2Field = ListBuffer[Card](Card(CardValue.Four, Suit.Spades))

        val playingField = new PlayingField(player1Hand, player2Hand)
        playingField.attack(player1Hand, player2Hand, player2Field)

        player1Hand shouldBe empty
        player2Field shouldBe empty
      }
    }
  }


