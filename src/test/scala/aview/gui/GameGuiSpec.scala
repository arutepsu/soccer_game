package aview.gui

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.swing._
import scala.swing.event._
import controller._
import model._
import scala.collection.mutable.Queue
import scala.collection.mutable
import javax.swing.SwingUtilities
import javax.swing.JOptionPane

class GameGuiSpec extends AnyWordSpec with Matchers {

  // Mock the Controller
  class MockController extends Controller(new PlayingField(mutable.Queue.empty, mutable.Queue.empty)) {
    var startGameCalled = false
    var nicknameEntered = ""
    var statusText = "Idle"

    override def startGame(): Unit = {
      startGameCalled = true
      statusText = "Game started"
      publish(new GameStarted)
    }

    override def enterNickname(nickname: String): Unit = {
      nicknameEntered = nickname
      publish(NicknameEntered(nickname))
    }

    override def getStatusText: String = statusText


    override def getPlayer1Hand: mutable.Queue[Card] = {
      mutable.Queue(Card(CardValue.Seven, Suit.Hearts), Card(CardValue.Two, Suit.Diamonds))
    }

    override def getPlayer2Hand: mutable.Queue[Card] = {
      mutable.Queue(Card(CardValue.Eight, Suit.Spades), Card(CardValue.Three, Suit.Clubs))
    }

    override def getPlayer1Field: mutable.ListBuffer[Card] = {
      mutable.ListBuffer(Card(CardValue.Nine, Suit.Hearts), Card(CardValue.Four, Suit.Diamonds))
    }

    override def getPlayer2Field: mutable.ListBuffer[Card] = {
      mutable.ListBuffer(Card(CardValue.Ten, Suit.Spades), Card(CardValue.Five, Suit.Clubs))
    }


    "A GameGui" should {
      "initialize properly" in {
        val controller = new MockController
        val gui = new GameGui(controller)

        SwingUtilities.invokeAndWait(new Runnable {
          def run(): Unit = {
            gui.title should be("Soccer Card Game")
            gui.nicknameField.columns should be(20)
            gui.statusline.text should be("Idle")
            gui.statusline.editable should be(false)
            gui.startButton.text should be("Start Game")
          }
        })
      }

      "show error dialog if nickname is empty" in {
        val controller = new MockController
        val gui = new GameGui(controller)

        SwingUtilities.invokeAndWait(new Runnable {
          def run(): Unit = {
            gui.nicknameField.text = ""
            gui.startButton.doClick()

            // Mocking the JOptionPane to intercept the dialog call
            val optionPane = new JOptionPane("Please enter your nickname.", JOptionPane.ERROR_MESSAGE)
            val dialog = optionPane.createDialog("Nickname Missing")

            dialog.isVisible should be(true)

            // Ensure the game does not start by checking the controller state.
            controller.startGameCalled should be(false)
          }
        })
      }

      "start game with valid nickname" in {
        val controller = new MockController
        val gui = new GameGui(controller)

        SwingUtilities.invokeAndWait(new Runnable {
          def run(): Unit = {
            gui.nicknameField.text = "Player1"
            gui.startButton.doClick()

            controller.nicknameEntered should be("Player1")
            controller.startGameCalled should be(true)
            gui.statusline.text should be("Game started")
          }
        })
      }

      "update player cards and fields" in {
        val controller = new MockController
        val gui = new GameGui(controller)

        SwingUtilities.invokeAndWait(new Runnable {
          def run(): Unit = {
            controller.startGame()
            gui.updatePlayerCardsAndFields()

            gui.player1CardsPanel.contents.size should be(2)
            gui.player1FieldPanel.contents.size should be(2)
            gui.player2CardsPanel.contents.size should be(2)
            gui.player2FieldPanel.contents.size should be(2)

            gui.player1CardsPanel.contents.head.asInstanceOf[Label].text should be("7 of Hearts")
            gui.player2CardsPanel.contents.head.asInstanceOf[Label].text should be("8 of Spades")
            gui.player1FieldPanel.contents.head.asInstanceOf[Label].text should be("9 of Hearts")
            gui.player2FieldPanel.contents.head.asInstanceOf[Label].text should be("10 of Spades")
          }
        })
      }

      "react to GameStarted event" in {
        val controller = new MockController
        val gui = new GameGui(controller)

        SwingUtilities.invokeAndWait(new Runnable {
          def run(): Unit = {
            controller.publish(new GameStarted)
            gui.statusline.text should be("Game started")
            gui.player1CardsPanel.contents.size should be(2)
            gui.player2CardsPanel.contents.size should be(2)
          }
        })
      }

      "react to FieldUpdated event" in {
        val controller = new MockController
        val gui = new GameGui(controller)

        SwingUtilities.invokeAndWait(new Runnable {
          def run(): Unit = {
            controller.publish(new FieldUpdated)
            gui.player1CardsPanel.contents.size should be(2)
            gui.player2CardsPanel.contents.size should be(2)
          }
        })
      }
    }
  }
}