import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter
import java.io.{ByteArrayOutputStream, StringReader}
import java.nio.charset.StandardCharsets
import model.{Card, Player, PlayingField, CardDeck}
import controller.Controller
import aview.Tui

import scala.collection.mutable
import scala.compiletime.uninitialized

class MainSpec extends AnyWordSpec with Matchers with BeforeAndAfter {

  var main: Main.type = uninitialized
  var controller: Option[Controller] = None
  var tui: Option[Tui] = None

  before {
    main = Main
    controller = Some(new Controller(new PlayingField(
      player1Cards = mutable.Queue.empty,
      player2Cards = mutable.Queue.empty
    )))
    tui = Some(new Tui(controller.get))
  }

  "The Main object" should {
    // Existing tests...

    "initialize the game correctly" in {
      val (player1, player2) = main.initializeGame("John")
      player1.name shouldBe "John"
      player2.name shouldBe "CPU"
      player1.cards should have size 26
      player2.cards should have size 26
    }

//    "run main method" in {
//      val outputStream = new ByteArrayOutputStream()
//      val inputStream = new StringReader("John\ninvalid command\nq\n")
//      Console.withOut(outputStream) {
//        Console.withIn(inputStream) {
//          main.main(Array.empty)
//        }
//      }
//      val output = new String(outputStream.toByteArray, StandardCharsets.UTF_8)
//      output should include("Welcome to the Soccer Card Game !!!")
//      output should include("Enter your name:")
//      output should include("Final Status of John:")
//      output should include("Final Status of CPU:")
//    }
  }
}
