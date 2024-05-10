import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import Model.{Card, PlayingField}
import util.Observable

class ControllerSpec extends WordSpec with MustMatchers {
  "A Controller" when {
    "created with a playing field" should {
      "have a playing field" in {
        val playingField = new PlayingField(
          player1Cards = mutable.Queue.empty,
          player2Cards = mutable.Queue.empty
        )
        val controller = new Controller(playingField)
        controller.playingField must be (playingField)
      }
    }

    "startGame is called" should {
      "prepare the game" in {
        val playingField = mock[PlayingField]
        playingField.gamePrepare() returns ()
        val controller = new Controller(playingField)
        controller.startGame("attackPos")
        verify(playingField).gamePrepare()
      }

      "prepare the fields" in {
        val playingField = mock[PlayingField]
        playingField.fieldPrepare(any, any) returns ()
        val controller = new Controller(playingField)
        controller.startGame("attackPos")
        verify(playingField, times(2)).fieldPrepare(any, any)
      }

      "play a round" in {
        val playingField = mock[PlayingField]
        playingField.playRound() returns ()
        val controller = new Controller(playingField)
        controller.startGame("attackPos")
        verify(playingField).playRound()
      }

      "notify observers" in {
        val observer = mock[Observer]
        val playingField = new PlayingField(
          player1Cards = mutable.Queue.empty,
          player2Cards = mutable.Queue.empty
        )
        val controller = new Controller(playingField)
        controller.addObserver(observer)
        controller.startGame("attackPos")
        verify(observer).update(any)
      }
    }
  }
}