import org.scalatest.wordspec.AnyWordSpec
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import Model.{Card, PlayingField}
import Card.*
import Model.Suit.*
import Model.CardObject.*


class PlayingFieldSpec extends AnyWordSpec {
  "A PlayingField" when {
    "gamePrepare is called" should {
      "initialize player hands with cards" in {
        val player1Cards = mutable.Queue.empty[Card]
        val player2Cards = mutable.Queue.empty[Card]
        val playingField = new PlayingField(player1Cards, player2Cards)
        playingField.gamePrepare()
        assert(playingField.getPlayer1Hand.size == 26)
        assert(playingField.getPlayer2Hand.size == 26)
      }
    }

    "fieldPrepare is called" should {
      "move cards from player hand to player field" in {
        val playerHand = mutable.Queue(Card(Ace, Spades), Card(Two, Hearts), Card(Three, Diamonds), Card(Four, Clubs))
        val playerField = ListBuffer.empty[Card]
        val playingField = new PlayingField(null, null) // We'll mock the parameters since they aren't relevant
        playingField.fieldPrepare(playerHand, playerField)
        assert(playerHand.isEmpty)
        assert(playerField.size == 4)
      }
    }
  }
}
