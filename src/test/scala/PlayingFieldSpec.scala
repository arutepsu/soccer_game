import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import Model._

class PlayingFieldSpec extends AnyWordSpec with Matchers {

  "A PlayingField" when {
    "initialized" should {
      "have 26 cards in each player's hand and then put 4 cards on the field" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.gamePrepare()
        playingField.getPlayer1Hand.size shouldEqual 26
        playingField.getPlayer2Hand.size shouldEqual 26
        playingField.fieldPrepare(playingField.getPlayer1Hand,playingField.getPlayer1Field)
        playingField.fieldPrepare(playingField.getPlayer2Hand,playingField.getPlayer2Field)
        playingField.getPlayer1Field.size shouldEqual(4)
        playingField.getPlayer2Field.size shouldEqual(4)
      }
    }

    "playing the game" should {
      "prepare the game and display initial state" in {
        val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
        playingField.playGame()
      }
    }
  }
}