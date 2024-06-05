package controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, FINISH, NOT_FINISH = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    FINISH ->"Game finish",
    NOT_FINISH ->"Game not finish")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}
