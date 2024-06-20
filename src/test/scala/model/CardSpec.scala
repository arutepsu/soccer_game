package model

import model.CardComponent.{Card, Suit}
import model.CardComponent.CardValue.*
import model.CardComponent.Suit.Suit
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CardSpec extends AnyWordSpec with Matchers {

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
          card.toString shouldEqual expected
        }
      }
    }

    "compared with another card" should {
      "return true if both cards have the same value and suit" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(Ace, Suit.Hearts)
        card1 shouldEqual card2
      }

      "return false if cards have different values or suits" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(King, Suit.Spades)
        card1 should not equal card2
      }

      "return true if two cards with the same value are compared" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(Ace, Suit.Clubs)
        card1.value shouldEqual card2.value
      }

      "return true if two cards with the same suit are compared" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(King, Suit.Hearts)
        card1.suit shouldEqual card2.suit
      }

      "return true if two cards with the same value but different suits are compared" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(Ace, Suit.Clubs)
        card1.value shouldEqual card2.value
        card1.suit should not equal card2.suit
      }

      "return true if two cards with the same suit but different values are compared" in {
        val card1 = Card(Ace, Suit.Hearts)
        val card2 = Card(King, Suit.Hearts)
        card1.suit shouldEqual card2.suit
        card1.value should not equal card2.value
      }
      "return 0 if both cards are the same" in {
        val card1 = Card(Ace, Suit.Hearts)
        card1.compare(card1.value, card1.value) shouldEqual 0
      }

      "return 0 if card1 and card2 have the same value when comparing directly" in {
        val card1 = Card(Ten, Suit.Hearts)
        val card2 = Card(Ten, Suit.Spades)
        card1.compare(card1.value, card2.value) shouldEqual 0
      }

      "return 0 if card1 and card2 have the same value and suit when comparing directly" in {
        val card1 = Card(Ten, Suit.Hearts)
        val card2 = Card(Ten, Suit.Hearts)
        card1.compare(card1.value, card2.value) shouldEqual 0
      }

      "return a positive value if card1 has a higher value than card2" in {
        val card1 = Card(Ace, Suit.Spades)
        val card2 = Card(King, Suit.Hearts)
        card1.compare(card1.value, card2.value) should be > 0
      }

      "return a negative value if card1 has a lower value than card2" in {
        val card1 = Card(Three, Suit.Diamonds)
        val card2 = Card(Ace, Suit.Spades)
        card1.compare(card1.value, card2.value) should be < 0
      }
    }

    "converted to integer value" should {
      "return correct integer value for each card" in {
        val testCases = Seq(
          (Ace, 14),
          (Two, 2),
          (Three, 3),
          (Four, 4),
          (Five, 5),
          (Six, 6),
          (Seven, 7),
          (Eight, 8),
          (Nine, 9),
          (Ten, 10),
          (Jack, 11),
          (Queen, 12),
          (King, 13)
        )

        testCases.foreach { case (value, expected) =>
          val card = Card(value, Suit.Hearts)
          card.valueToInt(card.value) shouldEqual expected
        }
      }
    }
  }
}
