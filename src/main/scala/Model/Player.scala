package Model

import Model.Card

import scala.collection.mutable

class Player(var nickname :String, var cards: List[Card]) {

  private def drawCards(cards: mutable.Queue[Card], count: Int): mutable.Queue[Card] = {
    cards.dequeueAll(_ => true).take(count)
  }

  //function so that player can name themself
  def myNameIs(): Unit = {
    val inputName = scala.io.StdIn.readLine()
  }
}
