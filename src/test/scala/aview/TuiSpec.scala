package aview

import controller.*
import controller.controllerComponent.ControllerBaseImpl.Controller
import model.PlayerComponent.Player
import model.PlayingFieldComponent.PlayingField
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {

  "A Tui" when {
    "initialized" should {
      val player1 = new Player("Player1", List.empty)
      val player2 = new Player("Player2", List.empty)
      val controller = new Controller(new PlayingField(
        player1Cards = scala.collection.mutable.Queue.empty,
        player2Cards = scala.collection.mutable.Queue.empty
      ))
      val tui = new Tui(controller)

      "display a welcome message" in {
        val output = captureOutput {
          tui.displayWelcomeMessage()
        }
        output should include("Welcome to the Soccer Card Game !!!")
      }

      "get the user name" in {
        val input = "TestUser"
        val output = captureInput(input) {
          tui.getUserName should be(input)
        }
        output should include("Enter your name:")
      }

      "display the final status of both players" in {
        val output = captureOutput {
          tui.displayFinalStatus(player1, player2)
        }
        output should include(s"Final Status of ${player1.name}:")
        output should include(s"Final Status of ${player2.name}:")
      }

      "process 'q' command correctly" in {
        val output = captureOutput {
          tui.processInputLine("q")
        }
        output shouldBe empty
      }

//      "process 'show' command correctly" in {
//        val output = captureOutput {
//          tui.processInputLine("show")
//        }
//        // Assuming the showMe() method in the controller prints something
//        output should include("Displaying the game field") // Replace with actual expected output
//      }

//      "process 'p' command correctly" in {
//        val output = captureOutput {
//          tui.processInputLine("p")
//        }
//        // Assuming the playGame() method in the controller prints something
//        output should include("Playing the game") // Replace with actual expected output
//      }

      "process 's' command correctly" in {
        val userName = "TestUser"
        val output = captureInput(userName) {
          tui.processInputLine("s")
        }
        output should include("Enter your name:")
        output should include(s"Nickname entered: $userName")
      }

//      "process 'u' command correctly" in {
//        val output = captureOutput {
//          tui.processInputLine("u")
//        }
//        // Assuming the undo() method in the controller prints something
//        output should include("Undoing last move") // Replace with actual expected output
//      }

//      "process 'r' command correctly" in {
//        val output = captureOutput {
//          tui.processInputLine("r")
//        }
//        // Assuming the redo() method in the controller prints something
//        output should include("Redoing last undone move") // Replace with actual expected output
//      }
//
//      "process 'd' command correctly" in {
//        val output = captureOutput {
//          tui.processInputLine("d")
//        }
//        // Assuming the doStep() method in the controller prints something
//        output should include("Doing a step") // Replace with actual expected output
//      }

      "process name input correctly" in {
        val name = "NewPlayer"
        val output = captureOutput {
          tui.processInputLine(name)
        }
        output should include(s"Nickname entered: $name")
      }

      "react to GameStarted event" in {
        val output = captureOutput {
          tui.reactions(new GameStarted)
        }
        output should include("Game has started!")
      }

      "react to GamePlayed event" in {
        val playerName = "TestPlayer"
        val output = captureOutput {
          tui.reactions(GamePlayed(playerName))
        }
        output should include("A move has been played!")
      }

      "react to FieldUpdated event" in {
        val output = captureOutput {
          tui.reactions(new FieldUpdated)
        }
        output should include("The field has been updated!")
      }

      "react to NicknameEntered event" in {
        val nickname = "TestNickname"
        val output = captureOutput {
          tui.reactions(NicknameEntered(nickname))
        }
        output should include(s"Nickname entered: $nickname")
      }

      "print status" in {
        val output = captureOutput {
          tui.printStatus
        }
        output should include(controller.getStatusText) // Assuming getStatusText() returns some text
      }
    }
  }

  private def captureOutput(f: => Unit): String = {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream)(f)
    stream.toString
  }

  private def captureInput(input: String)(f: => Unit): String = {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withIn(new java.io.ByteArrayInputStream(input.getBytes))(Console.withOut(stream)(f))
    stream.toString
  }
}
