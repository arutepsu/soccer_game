package aview
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.BeforeAndAfterEach
import controller.Controller
import model._

class TuiSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  var controller: Option[Controller] = None
  var tui: Option[Tui] = None

  override def beforeEach(): Unit = {
    controller = Some(new Controller(new PlayingField(
      player1Cards = scala.collection.mutable.Queue.empty,
      player2Cards = scala.collection.mutable.Queue.empty
    )))
    tui = Some(new Tui(controller.get))
  }

  "A Tui" when {
    "initialized" should {
      "display a welcome message" in {
        val welcomeMessage = "Welcome to the Card Game !!!"
        tui.get.displayWelcomeMessage()
      }
    }

//    "prompted for a username" should {
//      "return the entered username" in {
//        val userName = "TestUser"
//        // Mock user input somehow (e.g., using TestInputStream)
//        // setInput(userName)
//        val enteredName = tui.get.getUserName
//        enteredName shouldEqual userName
//      }
//    }

    "displaying final status" should {
      "print final status of players" in {
        val player1 = Player("Player1", List.empty)
        val player2 = Player("Player2", List.empty)

        tui.get.displayFinalStatus(player1, player2)
      }
    }
  }
}
