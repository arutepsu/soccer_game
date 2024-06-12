package aview

import controller._
import model.{Player, PlayingField}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {

  "A Tui" when {
    "initialized" should {
      val controller = new Controller(new PlayingField(player1Cards = scala.collection.mutable.Queue.empty,
        player2Cards = scala.collection.mutable.Queue.empty))
      val tui = new Tui(controller)

      "display a welcome message" in {
        // Capture the output and check for the welcome message
        val output = captureOutput(tui.displayWelcomeMessage())
        output should include("Welcome to the Soccer Card Game !!!")
      }

      "prompt for a username and return the entered name" in {
        this.synchronized {
          // Simulate user input and check the result
          val input = "TestUser"
          tui.processInputLine(input)
          // Assuming that processInputLine sets a username in the controller or tui
          controller.getCurrentPlayerName.shouldEqual("TestUser")  // Adjust this line according to your implementation
        }
      }

      "display the final status of both players" in {
        this.synchronized {
          val player1 = new Player("Player1", List.empty)
          val player2 = new Player("Player2", List.empty)

          // Simulate game end and check the final status output
          val output = captureOutput(tui.displayFinalStatus(player1, player2))
          output should include("Final Status of Player1:")
          output should include("Final Status of Player2:")
        }
      }

      "process input lines correctly" in {
        this.synchronized {
          // Simulate input processing
          val input = "play"
          tui.processInputLine(input)
          // Add assertions to verify the correct processing of the input
          // For example, checking the state change in the controller
          controller.getStatusText.shouldEqual(GameStatus.NOT_FINISH)
        }
      }
    }
  }

  private def captureOutput(f: => Unit): String = {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream)(f)
    stream.toString
  }
}
