import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.geometry._
import controller._
import model._
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scala.swing.Reactor

class GameGui(controller: Controller) extends JFXApp3 with Reactor {

  override def start(): Unit = {

    val startButton = new Button("Start Game")
    val undoButton = new Button("Undo")
    val redoButton = new Button("Redo")
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

    // Panels to display players' hands
    val player1HandPanel = new VBox()
    val player2HandPanel = new VBox()

    // Panels to display players' fields
    val player1FieldPanel = new Pane()
    val player2FieldPanel = new Pane()

    // Separate panels for hand and field
    val player1HandContainer = new VBox {
      spacing = 10
      children = Seq(player1HandPanel)
    }

    val player2HandContainer = new VBox {
      spacing = 10
      children = Seq(player2HandPanel)
    }

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
      children = Seq(startButton, undoButton, redoButton, showPlayer1HandButton, showPlayer2HandButton, playButton, doStepButton, quitButton)
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
        children = Seq(player1HandContainer, player2HandContainer, player1FieldContainer, player2FieldContainer)
      })
    }

    val fieldImageView = new ImageView(new Image("file:src/data/field.jpg")) {
      fitWidth = 800
      fitHeight = 600
      preserveRatio = true
      smooth = true
    }

    val mainScene = new Scene {
      root = new StackPane {
        children = Seq(
          fieldImageView,
          mainPanel
        )
      }
    }

    def clearHandPanels(): Unit = {
      player1HandPanel.children.clear()
      player2HandPanel.children.clear()
    }

    def clearFieldPanels(): Unit = {
      player1FieldPanel.children.clear()
      player2FieldPanel.children.clear()
    }

    def updatePlayerHandPanel(panel: VBox, hand: Seq[String], isLeft: Boolean): Unit = {
      val handBoxes = for (i <- 0 until (hand.length + 5) / 6) yield new HBox {
        spacing = 10
      }

      hand.zipWithIndex.foreach { case (card, index) =>
        val rowIndex = index / 6
        handBoxes(rowIndex).children.add(new ImageView(new Image(s"file:src/data/cards/$card")) {
          fitWidth = 50
          fitHeight = 70
          preserveRatio = true
          smooth = true
        })
      }

      // Convert handBoxes to a Seq of Node
      val nodes: Seq[javafx.scene.Node] = handBoxes.map(_.delegate)
      panel.children.addAll(nodes: _*)
      panel.alignment = if (isLeft) Pos.TopLeft else Pos.TopRight
    }

    def updatePlayerFieldPanel(panel: Pane, field: Seq[String]): Unit = {
      val positions = Seq(
        (150, 200), (300, 150),
        (100, 350), (300, 350)
      )

      panel.children.clear()  // Clear previous field cards

      field.take(4).zip(positions).foreach { case (card, (x, y)) =>
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
      updatePlayerFieldPanel(player1FieldPanel, controller.getPlayer1Field.map(_.fileName).toSeq)
      updatePlayerFieldPanel(player2FieldPanel, controller.getPlayer2Field.map(_.fileName).toSeq)
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
    }

    startButton.onAction = _ => {
      controller.startGame()
      statusline.text = controller.getStatusText
      placeInitialCards()
    }

    undoButton.onAction = _ => {
      controller.undo()
      statusline.text = controller.getStatusText
    }

    redoButton.onAction = _ => {
      controller.redo()
      statusline.text = controller.getStatusText
    }

    showPlayer1HandButton.onAction = _ => {
      clearHandPanels()
      updatePlayerHandPanel(player1HandPanel, controller.getPlayer1Hand.map(_.fileName).toSeq, isLeft = false)
    }

    showPlayer2HandButton.onAction = _ => {
      clearHandPanels()
      updatePlayerHandPanel(player2HandPanel, controller.getPlayer2Hand.map(_.fileName).toSeq, isLeft = true)
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

    // Update GUI based on controller events
    listenTo(controller)
    reactions += {
      case _: GameStarted =>
        statusline.text = controller.getStatusText
        placeInitialCards()
      case _: GamePlayed =>
        statusline.text = controller.getStatusText
      case _: FieldUpdated =>
        statusline.text = controller.getStatusText
        updatePlayerFieldPanel(player1FieldPanel, controller.getPlayer1Field.map(_.fileName).toSeq)
        updatePlayerFieldPanel(player2FieldPanel, controller.getPlayer2Field.map(_.fileName).toSeq)
      case NicknameEntered(nickname) => println(s"Nickname entered: $nickname")
    }
  }
}
