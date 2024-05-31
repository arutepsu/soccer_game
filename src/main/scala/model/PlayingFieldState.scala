package model
import model._
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.collection.mutable

trait PlayingFieldState {
  def handle(player1Hand: mutable.Queue[Card], player2Hand: mutable.Queue[Card], player1Field: ListBuffer[Card], player2Field: ListBuffer[Card]): Unit
}
