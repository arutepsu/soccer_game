package model


object CardValue {
  sealed trait CardValue

  case object Ace extends CardValue
  case object Two extends CardValue
  case object Three extends CardValue
  case object Four extends CardValue
  case object Five extends CardValue
  case object Six extends CardValue
  case object Seven extends CardValue
  case object Eight extends CardValue
  case object Nine extends CardValue
  case object Ten extends CardValue
  case object Jack extends CardValue
  case object Queen extends CardValue
  case object King extends CardValue

  // Define a custom ordering for CardValue
  implicit val cardValueOrdering: Ordering[CardValue] = new Ordering[CardValue] {
    override def compare(x: CardValue, y: CardValue): Int = {
      val values = List(Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)
      values.indexOf(x) - values.indexOf(y)
    }
  }
}
