package controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._
import util._
import java.util.concurrent.CountDownLatch

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val playingField = new PlayingField(
        player1Cards = scala.collection.mutable.Queue.empty,
        player2Cards = scala.collection.mutable.Queue.empty
      )
      val controller = new Controller(playingField)
      val observer: Observer = new Observer {


        override def update: Boolean = {
          updated = true; updated
        }
      }
      controller.add(observer)
      "notify its Observer after creation" in {
//        controller.startGame()
        controller.notifyObservers
        observer.updated shouldBe(true)
//      }
//      "notify its Observer after random creation" in {
//        controller.createRandomGrid(4, 1)
//        observer.updated should be(true)
//        controller.grid.valid should be(true)
//      }
//      "notify its Observer after setting a cell" in {
//        controller.set(1, 1, 4)
//        observer.updated should be(true)
//        controller.grid.cell(1, 1).value should be(4)
//      }
//      "notify its Observer after solving" in {
//        controller.solve
//        observer.updated should be(true)
//        controller.grid.solved should be(true)
    }
  }
}
}
