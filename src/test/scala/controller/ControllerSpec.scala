package controller

import model.{PlayingField, Card}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" when {
    "empty" should {
      val playingField = new PlayingField(
        player1Cards = scala.collection.mutable.Queue.empty,
        player2Cards = scala.collection.mutable.Queue.empty
      )
      val controller = new Controller(playingField)

      "handle undo/redo of game actions correctly" in {
        // Initially, the game should be in the IDLE state
        controller.gameStatus should be(GameStatus.IDLE)

        // Start the game and check status
        controller.startGame()
        controller.gameStatus should be(GameStatus.NOT_FINISH)

        // Perform some game actions and verify
        controller.playGame("Player1")
        controller.playingField.getPlayer1Field.nonEmpty shouldBe true

        // Undo the last action and check status
        controller.undo()
        controller.playingField.getPlayer1Field.nonEmpty shouldBe false

        // Redo the action and check status
        controller.redo()
        controller.playingField.getPlayer1Field.nonEmpty shouldBe true
      }
    }
  }
}
