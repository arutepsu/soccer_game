import model.*

import scala.io.StdIn
import aview.*
import aview.gui.*
import controller.Controller

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Main {
  val controller = new Controller(
    new PlayingField(
      player1Cards = mutable.Queue.empty,
      player2Cards = mutable.Queue.empty
    )
  )

  val tui = new Tui(controller)
  val gameGui = new GameGui(controller)
  gameGui.visible = true

  def initializeGame(username: String): (Player, Player) = {
    val player1 = Player(username, List.empty)
    val player2 = Player("CPU", List.empty)

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

    controller.startGame()
    controller.enterNickname(username)

    (updatedPlayer1, updatedPlayer2)
  }

  def main(args: Array[String]): Unit = {
    tui.displayWelcomeMessage()

    val username = tui.getUserName
    val (player1, player2) = initializeGame(username)

    var input = ""
    while (input != "q") {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }

    tui.displayFinalStatus(player1, player2)
  }
}
