package model.PlayingFieldComponent

import model.*
import model.CardComponent.Card

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Queue}

trait PlayingFieldState {
  def handle(player1Hand: mutable.Queue[Card], player2Hand: mutable.Queue[Card], player1Field: ListBuffer[Card], player2Field: ListBuffer[Card]): Unit
}
