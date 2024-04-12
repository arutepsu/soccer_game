package Model

import Model.Card

import scala.collection.mutable

class Player(var nickname :String, var cards: List[Card]) {

  //function so that player can name themself
  def myNameIs(): Unit = {
    val inputName = scala.io.StdIn.readLine()
  }
}
