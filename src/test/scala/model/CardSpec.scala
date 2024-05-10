package model

import model.*
import model.CardValue.*
import model.Suit.Suit
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec {

  "A Card" when {
    "created with any value and suit" should {
      "return the correct string representation" in {
        val testCases = Seq(
          (Ace, Suit.Hearts, "Ace of Hearts"),
          (Two, Suit.Clubs, "2 of Clubs"),
          (Three, Suit.Spades, "3 of Spades"),
          (Four, Suit.Diamonds, "4 of Diamonds"),
          (Five, Suit.Hearts, "5 of Hearts"),
          (Six, Suit.Clubs, "6 of Clubs"),
          (Seven, Suit.Spades, "7 of Spades"),
          (Eight, Suit.Diamonds, "8 of Diamonds"),
          (Nine, Suit.Hearts, "9 of Hearts"),
          (Ten, Suit.Clubs, "10 of Clubs"),
          (Jack, Suit.Spades, "Jack of Spades"),
          (Queen, Suit.Diamonds, "Queen of Diamonds"),
          (King, Suit.Hearts, "King of Hearts")
        )

        testCases.foreach { case (value, suit, expected) =>
          val card = Card(value, suit)
          assert(card.toString == expected)
        }
      }
    }

    "compared with another card" should {
      "return true if both cards have the same value and suit" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(Ace, Suit.Hearts)
        assert(card1 == card2)
      }

      "return false if cards have different values or suits" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(King, Suit.Spades)
        assert(card1 != card2)
      }
    }
  }
}
