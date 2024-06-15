package aview.gui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.geometry._
import controller._
import model._
import scala.swing.Reactor

class GameGui(controller: Controller) extends JFXApp3 with Reactor {

  override def start(): Unit = {

    val startButton = new Button("Start Game")
    val undoButton = new Button("Undo")
    val redoButton = new Button("Redo")
    val showPlayer1FieldButton = new Button("Show Player 1 Field")
    val showPlayer2FieldButton = new Button("Show Player 2 Field")
    val showPlayer1HandButton = new Button("Show Player 1 Hand")
    val showPlayer2HandButton = new Button("Show Player 2 Hand")
    val playButton = new Button("Play Move")
    val doStepButton = new Button("Do Step")
    val quitButton = new Button("Quit")
    val nicknameField = new TextField {
      promptText = "Enter Your Nickname"
    }
    val nicknameLabel = new Label("Enter Your Nickname:")
    val statusline = new TextField {
      text = controller.getStatusText
      editable = false
    }

    // Panels to display players' hands and fields
    val player1CardsPanel = new VBox()
    val player2CardsPanel = new VBox()
    val player1FieldPanel = new VBox()
    val player2FieldPanel = new VBox()
    val player1Label = new Label("Player 1's Cards:")
    val player2Label = new Label("Player 2's Cards:")
    val player1FieldLabel = new Label("Player 1's Field:")
    val player2FieldLabel = new Label("Player 2's Field:")

    // Left and Right Panels for Player 1 and Player 2
    val player1Panel = new VBox {
      spacing = 10
      children = Seq(player1Label, player1CardsPanel, player1FieldLabel, player1FieldPanel)
    }

    val player2Panel = new VBox {
      spacing = 10
      children = Seq(player2Label, player2CardsPanel, player2FieldLabel, player2FieldPanel)
    }

    val buttonPanel = new HBox {
      spacing = 10
      children = Seq(startButton, undoButton, redoButton, showPlayer1FieldButton, showPlayer2FieldButton, showPlayer1HandButton, showPlayer2HandButton, playButton, doStepButton, quitButton)
    }

    val nicknamePanel = new HBox {
      spacing = 10
      children = Seq(nicknameLabel, nicknameField)
    }

    val statusPanel = new HBox {
      spacing = 10
      children = Seq(statusline)
    }

    val mainPanel = new VBox {
      spacing = 20
      padding = Insets(20)
      children = Seq(buttonPanel, nicknamePanel, statusPanel, new HBox {
        spacing = 50
        children = Seq(player1Panel, player2Panel)
      })
    }

    stage = new JFXApp3.PrimaryStage {
      title = "Soccer Card Game"
      scene = new Scene {
        root = mainPanel
      }
    }

    startButton.onAction = _ => {
      val playerName = nicknameField.text.value
      if (playerName.isEmpty) {
        new Alert(Alert.AlertType.Error) {
          initOwner(stage)
          title = "Nickname Missing"
          headerText = "Please enter your nickname."
        }.showAndWait()
      } else {
        controller.enterNickname(playerName)
        controller.startGame()
        statusline.text = controller.getStatusText
      }
    }

    undoButton.onAction = _ => {
      controller.undo()
      statusline.text = controller.getStatusText
    }

    redoButton.onAction = _ => {
      controller.redo()
      statusline.text = controller.getStatusText
    }

    showPlayer1FieldButton.onAction = _ => {
      clearPanels()
      updatePlayer1FieldPanel()
      statusline.text = controller.getStatusText
    }

    showPlayer2FieldButton.onAction = _ => {
      clearPanels()
      updatePlayer2FieldPanel()
      statusline.text = controller.getStatusText
    }

    showPlayer1HandButton.onAction = _ => {
      clearPanels()
      updatePlayer1HandPanel()
    }

    showPlayer2HandButton.onAction = _ => {
      clearPanels()
      updatePlayer2HandPanel()
    }

    playButton.onAction = _ => {
      controller.playGame(controller.getCurrentPlayerName)
      statusline.text = controller.getStatusText
    }

    doStepButton.onAction = _ => {
      controller.doStep()
      statusline.text = controller.getStatusText
    }

    quitButton.onAction = _ => {
      sys.exit(0)
    }

    def clearPanels(): Unit = {
      player1CardsPanel.children.clear()
      player2CardsPanel.children.clear()
      player1FieldPanel.children.clear()
      player2FieldPanel.children.clear()
    }

    def updatePlayer1HandPanel(): Unit = {
      clearPanels()
      val player1Hand = controller.getPlayer1Hand.take(22)
      player1Hand.foreach { card =>
        player1CardsPanel.children.add(new Label(card.toString))
      }
    }

    def updatePlayer2HandPanel(): Unit = {
      clearPanels()
      val player2Hand = controller.getPlayer2Hand.take(22)
      player2Hand.foreach { card =>
        player2CardsPanel.children.add(new Label(card.toString))
      }
    }

    def updatePlayer1FieldPanel(): Unit = {
      clearPanels()
      val player1Field = controller.getPlayer1Field.take(4)
      player1Field.foreach { card =>
        player1FieldPanel.children.add(new Label(card.toString))
      }
    }

    def updatePlayer2FieldPanel(): Unit = {
      clearPanels()
      val player2Field = controller.getPlayer2Field.take(4)
      player2Field.foreach { card =>
        player2FieldPanel.children.add(new Label(card.toString))
      }
    }

    // Update GUI based on controller events
    listenTo(controller)
    reactions += {
      case _: GameStarted => statusline.text = controller.getStatusText
      case _: GamePlayed => statusline.text = controller.getStatusText
      case _: FieldUpdated => statusline.text = controller.getStatusText
      case NicknameEntered(nickname) => println(s"Nickname entered: $nickname")
    }
  }
}
