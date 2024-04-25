import org.scalatest.wordspec.AnyWordSpec
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import Model._
//import Model.Card
//import Model.Suit
//import Model.CardObject.*

class PlayingFieldSpec extends AnyWordSpec {
  "A PlayingField" when {
    "gamePrepare method is called" should {
      "initialize player hands with 26 cards each" in {
        val player1Cards = mutable.Queue.empty[Card]
        val player2Cards = mutable.Queue.empty[Card]
        val playingField = new PlayingField(player1Cards, player2Cards)
        playingField.gamePrepare()
        assert(playingField.getPlayer1Hand.size == 26)
        assert(playingField.getPlayer2Hand.size == 26)
      }
    }

    "fieldPrepare method is called" should {
      "move cards from player hand to player field" in {
        val playerHand = mutable.Queue(Card(CardValue.Four, Suit.Spades),
          Card(CardValue.Two, Suit.Hearts),
          Card(CardValue.Three, Suit.Diamonds),
          Card(CardValue.Four, Suit.Clubs))
        val playerField = ListBuffer.empty[Card]
        val playingField = new PlayingField(null, null)
        playingField.fieldPrepare(playerHand, playerField)
        assert(playerHand.isEmpty)
        assert(playerField.size == 4)
      }
    }
  }
}

//object PlayingFieldObject {
//  def main(args: Array[String]): Unit = {
//
//    val player1Cards = mutable.Queue.empty[Card]
//    val player2Cards = mutable.Queue.empty[Card]
//
//    val playingField = new PlayingField(player1Cards, player2Cards)
//    playingField.gamePrepare()
//    playingField.fieldPrepare(playingField.player1Hand,playingField.player1Field)
//    playingField.fieldPrepare(playingField.player2Hand,playingField.player2Field)
//
//    playingField.display()
//
//  }
//}