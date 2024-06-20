package model.CardComponent

object Suit {
  sealed trait Suit
  case object Hearts extends Suit
  case object Diamonds extends Suit
  case object Spades extends Suit
  case object Clubs extends Suit

  def suitToString(suit: Suit): String = suit match {
    case Hearts => "Hearts"
    case Diamonds => "Diamonds"
    case Spades => "Spades"
    case Clubs => "Clubs"
  }
}