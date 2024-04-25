package Model

case class Player(name: String, cards: List[String]) {
  override def toString: String = s"Player: $name, Cards: ${cards.mkString(", ")}"
}
