package model

import model.CardObject._
import model.Suit.Suit

case class Card(value: CardValue, suit: Suit) {
  override def toString: String = s"${valueToString(value)} of ${Suit.suitToString(suit)}"

  private def valueToString(value: CardValue): String = value match {
    case Ace => "Ace"
    case Two => "2"
    case Three => "3"
    case Four => "4"
    case Five => "5"
    case Six => "6"
    case Seven => "7"
    case Eight => "8"
    case Nine => "9"
    case Ten => "10"
    case Jack => "Jack"
    case Queen => "Queen"
    case King => "King"
  }

  def valueToInt(value: CardValue) : Int = value match {
    case Ace => 14
    case Two => 2
    case Three => 3
    case Four => 4
    case Five => 5
    case Six => 6
    case Seven => 7
    case Eight => 8
    case Nine => 9
    case Ten => 10
    case Jack => 11
    case Queen => 12
    case King => 13
  }
  def compare(card1: CardValue, card2: CardValue): Int = {
    val value : Int = valueToInt(card1) - valueToInt(card2)
    if (value == -12) {
      1
    } else {
      value
    }
  }
}