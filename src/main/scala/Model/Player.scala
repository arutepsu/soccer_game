package Model

case class Player(nickname: String, cards: List[String]) {
  override def toString: String = s"Player: $nickname, Cards: ${cards.mkString(", ")}"
}
