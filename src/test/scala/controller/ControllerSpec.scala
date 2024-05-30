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
        controller.notifyObservers
        observer.updated shouldBe(true)
    }
  }
}
}
