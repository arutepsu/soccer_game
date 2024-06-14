package controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model.{PlayingField, Card}
import util.{UndoManager, Command}
import scala.collection.mutable

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.mutable

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {

    "start a game" in {
      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
      val controller = new Controller(playingField)

      controller.startGame()

      playingField.getPlayer1Hand.size should be(22)
      playingField.getPlayer2Hand.size should be(22)
      playingField.getPlayer1Field.size should be(4)
      playingField.getPlayer2Field.size should be(4)
      controller.gameStatus should be(GameStatus.NOT_FINISH)
    }

//    "play a game" in {
//      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
//      val controller = new Controller(playingField)
//
//      controller.startGame()
//      controller.playGame("player1")
//
//      playingField.getPlayer1Hand.size should be < 26
//    }

    "show the field" in {
      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
      val controller = new Controller(playingField)

      controller.startGame()
      controller.showMe()
    }

    "do an undo step" in {
      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
      val controller = new Controller(playingField)

      controller.doStep()
    }

    "undo a step" in {
      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
      val controller = new Controller(playingField)

      controller.undo()
    }

    "redo a step" in {
      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
      val controller = new Controller(playingField)

      controller.redo()
    }

    "enter a nickname" in {
      val playingField = new PlayingField(mutable.Queue.empty, mutable.Queue.empty)
      val controller = new Controller(playingField)
      controller.enterNickname("testPlayer")
      controller.getCurrentPlayerName should be("testPlayer")
    }
  }
}