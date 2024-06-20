package model

import model.CardComponent.Card
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.{ListBuffer, Queue}

class GameOverStateSpec extends AnyWordSpec {
  "GameOverState" should {
    "print 'Game Over!' message" in {
      val player1Hand = Queue[Card]()
      val player2Hand = Queue[Card]()
      val player1Field = ListBuffer[Card]()
      val player2Field = ListBuffer[Card]()

      val outCapture = new java.io.ByteArrayOutputStream()
      Console.withOut(outCapture) {
        GameOverState.handle(player1Hand, player2Hand, player1Field, player2Field)
      }
      assert(outCapture.toString.contains("Game Over!"))
    }
  }
}
