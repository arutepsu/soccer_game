import model.*
import aview.gui.*
import controller.controllerComponent.ControllerBaseImpl.Controller
import model.CardComponent.{Card, CardDeck}
import model.PlayerComponent.Player
import model.PlayingFieldComponent.PlayingField

import scala.collection.mutable

object Main {

  val controller = new Controller(
    new PlayingField(
      player1Cards = mutable.Queue.empty,
      player2Cards = mutable.Queue.empty
    )
  )

  def main(args: Array[String]): Unit = {
    // Initialize the game with default player names
    val (player1, player2) = initializeGame()

    // Launch the GUI
    new GameGui(controller).main(args)
  }

  def initializeGame(): (Player, Player) = {
    val player1 = Player("Player1", List.empty)
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

    // Start the game
    controller.startGame()

    (updatedPlayer1, updatedPlayer2)
  }
}
