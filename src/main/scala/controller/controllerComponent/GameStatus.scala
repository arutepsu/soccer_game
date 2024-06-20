package controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, FINISH, NOT_FINISH, REDO, UNDO, STARTED, RUNNING = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    IDLE -> "",
    FINISH ->"Game finish",
    NOT_FINISH ->"Game not finish",
    REDO -> "Redone one step",
    UNDO -> "Undone one step",
    STARTED -> "Game started",
    RUNNING -> "Game is running")

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }
}