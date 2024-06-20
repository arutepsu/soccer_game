package model
import model.*
import model.CardComponent.Card
import model.PlayingFieldComponent.PlayingFieldState

import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.collection.mutable

object GameOverState extends PlayingFieldState {
  override def handle(player1Hand: mutable.Queue[Card], player2Hand: mutable.Queue[Card], player1Field: ListBuffer[Card], player2Field: ListBuffer[Card]): Unit = {
    println("Game Over!")
  }
}