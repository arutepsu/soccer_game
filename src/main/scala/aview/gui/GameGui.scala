package aview.gui

import scala.swing._
import scala.swing.event._
import controller._

class GameGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Soccer Card Game"

  val startButton = new Button("Start Game")
  val nicknameField = new TextField {
    columns = 20
  }
  val nicknameLabel = new Label("Enter Your Nickname:")
  val statusline = new TextField(controller.getStatusText, 20)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += startButton
    }
    contents += new FlowPanel {
      contents += nicknameLabel
      contents += nicknameField
    }
    contents += new FlowPanel {
      contents += statusline
    }
  }

  listenTo(startButton)

  var playerName = ""

  reactions += {
    case ButtonClicked(`startButton`) =>
      playerName = nicknameField.text
      if (playerName.isEmpty) {
        Dialog.showMessage(contents.head, "Please enter your nickname.", title="Nickname Missing", Dialog.Message.Error)
      } else {
        controller.startGame()
        controller.enterNickname(playerName)
      }

    case _: GameStarted =>
      statusline.text = controller.getStatusText

    case _: GamePlayed =>
      statusline.text = controller.getStatusText

    case _: FieldUpdated =>
      statusline.text = controller.getStatusText

    case NicknameEntered(nickname) =>
      println(s"Nickname entered: $nickname")  // This could be used to update UI or log the nickname
  }

  visible = true
}
