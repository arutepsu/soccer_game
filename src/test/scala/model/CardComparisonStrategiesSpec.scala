package model

import org.scalatest.wordspec.AnyWordSpec
import model.*
import model.CardComponent.{Card, CardComparisonStrategies, CardValue, Suit}


class CardComparisonStrategiesSpec extends AnyWordSpec {

  "A StandardComparison strategy" should {
    "correctly compare two cards based on their value" in {
      val card1 = Card(CardValue.Five, Suit.Spades)
      val card2 = Card(CardValue.Ten, Suit.Clubs)

      val comparison = CardComparisonStrategies.StandardComparison.compare(card1, card2)

//      comparison shouldBe ...
    }
  }
}