package model

case class Player(name: String, cards: List[String]) {
  override def toString: String = s"Player: $name, Cards: ${cards.mkString(", ")}"
}
//need to work on this class later