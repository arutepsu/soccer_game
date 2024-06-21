import model.*
import aview.gui.*
import aview.*
import controller.controllerComponent.ControllerBaseImpl._
import model.CardComponent.{Card, CardDeck}
import model.PlayerComponent.Player
import model.PlayingFieldComponent.PlayingField

import scala.collection.mutable
import scala.io.StdIn.readLine

object Main {

  val controller = new Controller(
    new PlayingField(
      player1Cards = mutable.Queue.empty,
      player2Cards = mutable.Queue.empty
    )
  )

  def main(args: Array[String]): Unit = {
    // Initialize the TUI
    val tui = new Tui(controller)
    tui.displayWelcomeMessage()

    // Get player names
    val player1Name = tui.getUser1Name
    val player2Name = tui.getUser2Name

    // Initialize the game with player names
    val (player1, player2) = initializeGame(player1Name, player2Name)

    // Main loop for TUI interaction
    var input: String = ""
    var currentPlayer = player1Name
    while (input != "q"){
      println(s"\n$currentPlayer's turn. Choose an action: (attack: 'a', redo: 'r', undo: 'u', quit: 'q')")
      input = readLine()
      tui.processInputLine(input, currentPlayer)
      currentPlayer = if (currentPlayer == player1Name) player2Name else player1Name
    } 

    // Display final status
    tui.displayFinalStatus(player1, player2)
  }

  def initializeGame(player1Name: String, player2Name: String): (Player, Player) = {
    val player1 = Player(player1Name, List.empty)
    val player2 = Player(player2Name, List.empty)

    // Create and shuffle the deck
    val deck = CardDeck.createStandardDeck()
    CardDeck.shuffleDeck(deck)

    // Distribute 26 cards to each player
    val player1Cards = mutable.Queue[Card]()
    val player2Cards = mutable.Queue[Card]()

    for (_ <- 1 to 26) {
      player1Cards.enqueue(deck.dequeue())
      player2Cards.enqueue(deck.dequeue())
    }

    // Set the cards in the playing field
    controller.playingField.setPlayer1Cards(player1Cards)
    controller.playingField.setPlayer2Cards(player2Cards)

    // Reassign the players with their respective cards
    val updatedPlayer1 = player1.copy(cards = player1Cards.map(_.toString).toList)
    val updatedPlayer2 = player2.copy(cards = player2Cards.map(_.toString).toList)

    // Start the game
    controller.startGame()

    (updatedPlayer1, updatedPlayer2)
  }
}
