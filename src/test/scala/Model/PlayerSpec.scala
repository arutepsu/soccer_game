package Model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" should {
    "be created with a nickname and a list of cards" in {
      val player = new Model.Player("Your Name", List("Card1", "Card2", "Card3"))
      player.name shouldEqual "Your Name"
      player.cards should contain allOf ("Card1", "Card2", "Card3")
    }
    "have a name" in {
      val player = new Model.Player("Your Name", List("Card1", "Card2", "Card3"))
      player.name should be("Your Name")
    }
    "have a nice String representation" in {
      val player = new Model.Player("Your Name", List("Card1", "Card2", "Card3"))
      player.toString should be("Player: Your Name, Cards: Card1, Card2, Card3")
    }
  }
}
