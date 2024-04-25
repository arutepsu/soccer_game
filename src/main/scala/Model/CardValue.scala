package Model

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
}

//  def compare(cardValue1: CardValue, cardValue2: CardValue): Int =
//    (cardValue1, cardValue2) match {
//      case (Ace, Ace) => 0
//            case (Ace, _) => 1
//            case (_, Ace) => -1
//            case (Two, Two) => 0
//            case (Two, _) => 1
//            case (_, Two) => -1
////            case (Ace, Two) => -1
////            case (Two, Ace) => 1
//            case (Three, Three) => 0
//            case (Three, _) => 1
//            case (_, Three) => -1
//            case (Four, Four) => 0
//            case (Four, _) => 1
//            case (_, Four) => -1
//            case (Five, Five) => 0
//            case (Five, _) => 1
//            case (_, Five) => -1
//            case (Six, Six) => 0
//            case (Six, _) => 1
//            case (_, Six) => -1
//            case (Seven, Seven) => 0
//            case (Seven, _) => 1
//            case (_, Seven) => -1
//            case (Eight, Eight) => 0
//            case (Eight, _) => 1
//            case (_, Eight) => -1
//            case (Nine, Nine) => 0
//            case (Nine, _) => 1
//            case (_, Nine) => -1
//            case (Ten, Ten) => 0
//            case (Ten, _) => 1
//            case (_, Ten) => -1
//            case (Jack, Jack) => 0
//            case (Jack, _) => 1
//            case (_, Jack) => -1
//            case (Queen, Queen) => 0
//            case (Queen, _) => 1
//            case (_, Queen) => -1
//            case (King, King) => 0
//    }
//}
