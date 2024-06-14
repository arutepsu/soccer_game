package aview.gui

import scala.swing._
import scala.swing.event._
import controller._
import model._

class GameGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Soccer Card Game"

  val startButton = new Button("Start Game")
  val undoButton = new Button("Undo")
  val redoButton = new Button("Redo")
  val nicknameField = new TextField {
    columns = 20
  }
  val nicknameLabel = new Label("Enter Your Nickname:")
  val statusline = new TextField(controller.getStatusText, 20) {
    editable = false
  }

  // Panels to display players' hands and fields
  val player1CardsPanel = new BoxPanel(Orientation.Vertical)
  val player2CardsPanel = new BoxPanel(Orientation.Vertical)
  val player1FieldPanel = new BoxPanel(Orientation.Vertical)
  val player2FieldPanel = new BoxPanel(Orientation.Vertical)
  val player1Label = new Label("Player 1's Cards:")
  val player2Label = new Label("Player 2's Cards:")
  val player1FieldLabel = new Label("Player 1's Field:")
  val player2FieldLabel = new Label("Player 2's Field:")

  // Left and Right Panels for Player 1 and Player 2
  val player1Panel = new BoxPanel(Orientation.Vertical) {
    contents += player1Label
    contents += player1CardsPanel
    contents += player1FieldLabel
    contents += player1FieldPanel
  }

  val player2Panel = new BoxPanel(Orientation.Vertical) {
    contents += player2Label
    contents += player2CardsPanel
    contents += player2FieldLabel
    contents += player2FieldPanel
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += startButton
      contents += undoButton
      contents += redoButton
    }
    contents += new FlowPanel {
      contents += nicknameLabel
      contents += nicknameField
    }
    contents += new FlowPanel {
      contents += statusline
    }
    contents += new GridPanel(1, 2) {
      contents += player1Panel
      contents += player2Panel
    }
  }

  listenTo(startButton, undoButton, redoButton)

  var playerName = ""

  reactions += {
    case ButtonClicked(`startButton`) =>
      playerName = nicknameField.text
      if (playerName.isEmpty) {
        Dialog.showMessage(contents.head, "Please enter your nickname.", title = "Nickname Missing", Dialog.Message.Error)
      } else {
        controller.enterNickname(playerName)
        controller.startGame()
        updatePlayerCardsAndFields()
        statusline.text = controller.getStatusText
      }

    case ButtonClicked(`undoButton`) =>
      controller.undo()
      updatePlayerCardsAndFields()
      statusline.text = controller.getStatusText

    case ButtonClicked(`redoButton`) =>
      controller.redo()
      updatePlayerCardsAndFields()
      statusline.text = controller.getStatusText

    case _: GameStarted =>
      updatePlayerCardsAndFields()
      statusline.text = controller.getStatusText

    case _: GamePlayed =>
      updatePlayerCardsAndFields()
      statusline.text = controller.getStatusText

    case _: FieldUpdated =>
      updatePlayerCardsAndFields()
      statusline.text = controller.getStatusText

    case NicknameEntered(nickname) =>
      println(s"Nickname entered: $nickname")
  }

  def updatePlayerCardsAndFields(): Unit = {
    player1CardsPanel.contents.clear()
    player2CardsPanel.contents.clear()
    player1FieldPanel.contents.clear()
    player2FieldPanel.contents.clear()

    val player1Hand = controller.getPlayer1Hand
    val player2Hand = controller.getPlayer2Hand
    val player1Field = controller.getPlayer1Field
    val player2Field = controller.getPlayer2Field

    player1Hand.foreach { card =>
      player1CardsPanel.contents += new Label(card.toString)
    }

    player2Hand.foreach { card =>
      player2CardsPanel.contents += new Label(card.toString)
    }

    player1Field.foreach { card =>
      player1FieldPanel.contents += new Label(card.toString)
    }

    player2Field.foreach { card =>
      player2FieldPanel.contents += new Label(card.toString)
    }

    player1CardsPanel.revalidate()
    player1CardsPanel.repaint()
    player2CardsPanel.revalidate()
    player2CardsPanel.repaint()
    player1FieldPanel.revalidate()
    player1FieldPanel.repaint()
    player2FieldPanel.revalidate()
    player2FieldPanel.repaint()
  }

  visible = true
}
