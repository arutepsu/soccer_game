import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter
import java.io.{ByteArrayOutputStream, StringReader}
import java.nio.charset.StandardCharsets
import model.{Player, PlayingField}
import controller.Controller
import aview.Tui
import scala.compiletime.uninitialized

class MainSpec extends AnyWordSpec with Matchers with BeforeAndAfter {

  var main: Main.type = uninitialized
  var controller: Option[Controller] = None
  var tui: Option[Tui] = None

  before {
    main = Main
    controller = Some(new Controller(new PlayingField(
      player1Cards = scala.collection.mutable.Queue.empty,
      player2Cards = scala.collection.mutable.Queue.empty
    )))
    tui = Some(new Tui(controller.get))
  }

  "The Main object" when {
    "started" should {
      "display welcome message" in {
        val outputStream = new ByteArrayOutputStream()
        Console.withOut(outputStream) {
          tui.get.displayWelcomeMessage()
        }
        val output = new String(outputStream.toByteArray, StandardCharsets.UTF_8)
        output should include("Welcome to the Soccer Card Game !!!")
      }

      "prompt user for name" in {
        val inputStream = new StringReader("John\n")
        Console.withIn(inputStream) {
          val username = tui.get.getUserName
          username shouldBe "John"
        }
      }

      "start the game" in {
        controller.get.startGame()
        controller.get.playingField.getPlayer1Hand should not be empty
        controller.get.playingField.getPlayer2Hand should not be empty
      }

      "display final status" in {
        val player1 = Player("John", List.empty)
        val player2 = Player("CPU", List.empty)
        val outputStream = new ByteArrayOutputStream()
        Console.withOut(outputStream) {
          tui.get.displayFinalStatus(player1, player2)
        }
        val output = new String(outputStream.toByteArray, StandardCharsets.UTF_8)
        output should include(s"Final Status of ${player1.name}:")
        output should include(s"Final Status of ${player2.name}:")
      }
    }
  }
}
