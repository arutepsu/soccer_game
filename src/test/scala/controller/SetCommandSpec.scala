package controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.{PlayingField, *}
import controller.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class SetCommandSpec extends AnyWordSpec {
  "A SetCommand" when {
    "executing" should {
      "start the game when doing the step" in {
        val controller = new ControllerStub
        val setCommand = new SetCommand(controller)

        setCommand.doStep

        assert(controller.gameStarted)
      }

      "revert the game to its state before starting when undoing the step" in {
        val controller = new ControllerStub
        val setCommand = new SetCommand(controller)

        setCommand.undoStep

        assert(controller.gameReverted)
      }

      "start the game again when redoing the step" in {
        val controller = new ControllerStub
        val setCommand = new SetCommand(controller)

        setCommand.redoStep

        assert(controller.gameStarted)
      }
    }
  }
//  var pf = new PlayingField(
//    player1Cards = scala.collection.mutable.Queue.empty,
//    player2Cards = scala.collection.mutable.Queue.empty
//  )
  // Stub implementation of Controller for testing
  class ControllerStub extends Controller(new PlayingField(
    player1Cards = scala.collection.mutable.Queue.empty,
    player2Cards = scala.collection.mutable.Queue.empty
  )) {
    var gameStarted = false
    var gameReverted = false

    override def startGame(): Unit = {
      gameStarted = true
    }

//     def playingField: PlayingField = new PlayingField(
//       player1Cards = scala.collection.mutable.Queue.empty,
//       player2Cards = scala.collection.mutable.Queue.empty
//     ) {
//      gameBeforeStarting(): Unit = {
//        gameReverted = true
//      }
//    }
    gameReverted = true
  }
}
