package model

import model.*
import model.CardValue.*
import org.scalatest.wordspec.AnyWordSpec

class CardValueOrderingSpec extends AnyWordSpec {

  "CardValueOrdering" should {
    "correctly compare card values" in {
      val ordering = CardValueOrdering

      // Define pairs of card values for comparison
      val testCases = Seq(
        (Ace, Two),
        (Two, Three),
        (Three, Four),
        (Four, Five),
        (Five, Six),
        (Six, Seven),
        (Seven, Eight),
        (Eight, Nine),
        (Nine, Ten),
        (Ten, Jack),
        (Jack, Queen),
        (Queen, King)
      )
      
      testCases.foreach { case (value1, value2) =>
        assert(ordering.compare(value1, value2) > 0)
        assert(ordering.compare(value2, value1) < 0) 
      }
      
      val allValues = Seq(Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)
      allValues.foreach { value =>
        assert(ordering.compare(value, value) == 0)
      }
    }
  }
}
