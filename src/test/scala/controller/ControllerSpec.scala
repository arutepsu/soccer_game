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
      val latch = new CountDownLatch(1) // Create a latch with count 1
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update: Unit = {
          updated = true
          latch.countDown() // Signal that update has occurred
        }
      }

      "notify its Observer after starting the game" in {
        controller.add(observer)
        controller.startGame()
        latch.await() // Wait until the latch count reaches 0
        observer.isUpdated shouldBe true
      }
    }
  }
}
