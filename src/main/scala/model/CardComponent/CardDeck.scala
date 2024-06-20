package model.CardComponent

import model.CardComponent.Card
import model.CardComponent.Card.*
import model.CardComponent.CardValue.*
import Suit.{Clubs, Diamonds, Hearts, Spades}

import scala.collection.mutable
import scala.collection.mutable.Queue
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
    val buffer = deck.toBuffer
    val shuffledBuffer = Random.shuffle(buffer)
    deck.clear()
    shuffledBuffer.foreach(deck.enqueue)
  }
  /*def main(args: Array[String]): Unit = {
    val deck = createStandardDeck()
    println("Standard Deck of Cards:")
    deck.foreach(println)

    shuffleDeck(deck)
    println("\nShuffled Deck of Cards:")
    deck.foreach(println)
  }*/
}
