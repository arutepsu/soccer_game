package aview.gui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.geometry._
import controller._
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scala.swing.Reactor
import scalafx.application.Platform
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import controller.GameEvents._

class GameGui(controller: Controller) extends JFXApp3 with Reactor {

  override def start(): Unit = {

    val undoButton = new Button("Undo")
    val redoButton = new Button("Redo")
    val doStepButton = new Button("Do Step")
    val attackButton = new Button("Attack")
    val quitButton = new Button("Quit")
    val nicknameField1 = new TextField {
      promptText = "Enter Player 1 Nickname"
    }
    val nicknameLabel1 = new Label("Enter Player 1 Nickname:")

    val nicknameField2 = new TextField {
      promptText = "Enter Player 2 Nickname"
    }
    val nicknameLabel2 = new Label("Enter Player 2 Nickname:")

    val statusline = new TextField {
      text = controller.getStatusText
      editable = false
    }

    val player1FieldPanel = new Pane()
    val player2FieldPanel = new Pane()

    val player1FieldContainer = new VBox {
      spacing = 10
      children = Seq(player1FieldPanel)
    }

    val player2FieldContainer = new VBox {
      spacing = 10
      children = Seq(player2FieldPanel)
    }

    val buttonPanel = new HBox {
      spacing = 10
      children = Seq(undoButton, redoButton, doStepButton, attackButton, quitButton)
    }

    val nicknamePanel = new VBox {
      spacing = 10
      children = Seq(
        new HBox {
          spacing = 10
          children = Seq(nicknameLabel1, nicknameField1)
        },
        new HBox {
          spacing = 10
          children = Seq(nicknameLabel2, nicknameField2)
        }
      )
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
        children = Seq(player1FieldContainer, player2FieldContainer)
      })
    }

    val fieldImageView = new ImageView(new Image("file:src/data/field.jpg")) {
      fitWidth = 800
      fitHeight = 800
      preserveRatio = true
      smooth = true
    }

    val fixedPane = new Pane {
      prefWidth = 800
      prefHeight = 600
      children = Seq(fieldImageView, mainPanel)

      layoutBounds.onChange { (_, _, bounds) =>
        fieldImageView.layoutX = (bounds.getWidth - fieldImageView.fitWidth()) / 2
        fieldImageView.layoutY = (bounds.getHeight - fieldImageView.fitHeight()) / 2
      }
    }

    val mainScene = new Scene {
      root = new StackPane {
        children = Seq(fixedPane)
        StackPane.setAlignment(fixedPane, Pos.Center)
      }
    }

    def clearFieldPanels(): Unit = {
      player1FieldPanel.children.clear()
      player2FieldPanel.children.clear()
    }

    def updatePlayerFieldPanel(panel: Pane, field: Seq[String], positions: Seq[(Double, Double)], nickname: String): Unit = {
      panel.children.clear()
      val nicknameLabel = new Label(nickname) {
        layoutX = 0
        layoutY = 0
      }
      panel.children.add(nicknameLabel)
      field.zip(positions).foreach { case (card, (x, y)) =>
        val cardImageView = new ImageView(new Image(s"file:src/data/cards/$card")) {
          fitWidth = 50
          fitHeight = 70
          preserveRatio = true
          smooth = true
        }
        cardImageView.layoutX = x
        cardImageView.layoutY = y
        panel.children.add(cardImageView)
      }
    }

    def placeInitialCards(): Unit = {
      val player1Positions = Seq(
        (713.0, 50.0),
        (613.0, 150.0),
        (713.0, 150.0),
        (813.0, 150.0)
      )
      val player2Positions = Seq(
        (-198.0, 395.0),
        (-98.0, 295.0),
        (-198.0, 295.0),
        (-298.0, 295.0)
      )

      updatePlayerFieldPanel(player1FieldPanel, controller.getPlayer1Field.map(_.fileName).toSeq, player1Positions, controller.getCurrentPlayer1Name)
      updatePlayerFieldPanel(player2FieldPanel, controller.getPlayer2Field.map(_.fileName).toSeq, player2Positions, controller.getCurrentPlayer2Name)
    }

    val introImageView = new ImageView(new Image("file:src/data/logo.png")) {
      fitWidth = 400
      fitHeight = 300
      preserveRatio = true
      smooth = true
    }

    val nicknameScene = new Scene {
      root = new VBox {
        spacing = 20
        padding = Insets(20)
        alignment = Pos.Center
        children = Seq(
          new Label("Welcome to Soccer Game!"),
          introImageView,
          nicknamePanel,
          new Button("Start") {
            onAction = _ => {
              val playerName1 = nicknameField1.text.value
              val playerName2 = nicknameField2.text.value
              if (playerName1.isEmpty || playerName2.isEmpty) {
                new Alert(Alert.AlertType.Error) {
                  initOwner(stage)
                  title = "Nickname Missing"
                  headerText = "Please enter nicknames for both players."
                }.showAndWait()
              } else {
                controller.enterNicknames(playerName1, playerName2)
                controller.startGame()
                statusline.text = controller.getStatusText
                stage.scene = mainScene
                placeInitialCards()
              }
            }
          }
        )
      }
    }

    stage = new JFXApp3.PrimaryStage {
      title = "Soccer Card Game"
      scene = nicknameScene
      icons.add(new Image("file:src/data/logo.png"))
    }

    undoButton.onAction = _ => {
      controller.undo()
      Platform.runLater {
        statusline.text = controller.getStatusText
        placeInitialCards() // Update the fields after undo
      }
    }

    redoButton.onAction = _ => {
      controller.redo()
      Platform.runLater {
        statusline.text = controller.getStatusText
        placeInitialCards() // Update the fields after redo
      }
    }

    doStepButton.onAction = _ => {
      controller.doStep()
      Platform.runLater {
        statusline.text = controller.getStatusText
        placeInitialCards() // Update the fields after step
      }
    }

    attackButton.onAction = _ => {
      Future {
        controller.playGame(controller.getCurrentPlayer1Name, controller.getCurrentPlayer2Name)
        Platform.runLater {
          statusline.text = controller.getStatusText
          placeInitialCards() // Update the fields after attack
        }
      }
    }

    quitButton.onAction = _ => {
      sys.exit(0)
    }

    listenTo(controller)
    reactions += {
      case _: GameStarted =>
        Platform.runLater {
          statusline.text = controller.getStatusText
          placeInitialCards()
        }
      case _: GamePlayed =>
        Platform.runLater {
          statusline.text = controller.getStatusText
        }
      case _: FieldUpdated =>
        Platform.runLater {
          statusline.text = controller.getStatusText
          placeInitialCards() // Update the fields after field update
        }
      case NicknamesEntered(nickname1, nickname2) =>
        println(s"Nicknames entered: Player 1: $nickname1, Player 2: $nickname2")
    }
  }
}

