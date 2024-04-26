package Model

import CardValue._
import Suit._

import scala.collection.mutable
import scala.util.Random

object CardDeck {
  def createStandardDeck(): mutable.Queue[Card] = {
    val deck = mutable.Queue[Card]()
    val cardValues = List(Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)
    val suits = List(Hearts, Diamonds, Spades, Clubs)

    for {
      value <- cardValues
      suit <- suits
    } {
      deck.enqueue(Card(value, suit))
    }
    deck
  }

  def shuffleDeck(deck: mutable.Queue[Card]): Unit = {
    val shuffledBuffer = Random.shuffle(deck)
    deck.clear()
    shuffledBuffer.foreach(deck.enqueue)
  }
}


