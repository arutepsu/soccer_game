import org.scalatest.wordspec.AnyWordSpec
import Model.CardValue._
import Model._

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

      // Check that for each pair, the first value is greater than the second
      testCases.foreach { case (value1, value2) =>
        assert(ordering.compare(value1, value2) > 0)
      }

      // Also check for equality
      assert(ordering.compare(Ace, Ace) == 0)
      assert(ordering.compare(Two, Two) == 0)
      assert(ordering.compare(Three, Three) == 0)
      assert(ordering.compare(Four, Four) == 0)
      assert(ordering.compare(Five, Five) == 0)
      assert(ordering.compare(Six, Six) == 0)
      assert(ordering.compare(Seven, Seven) == 0)
      assert(ordering.compare(Eight, Eight) == 0)
      assert(ordering.compare(Nine, Nine) == 0)
      assert(ordering.compare(Ten, Ten) == 0)
      assert(ordering.compare(Jack, Jack) == 0)
      assert(ordering.compare(Queen, Queen) == 0)
      assert(ordering.compare(King, King) == 0)
    }
  }
}
