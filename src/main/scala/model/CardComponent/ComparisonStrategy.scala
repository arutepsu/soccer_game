package model.CardComponent

import model.*
import model.CardComponent.Card

trait ComparisonStrategy {
  def compare(attackingCard: Card, defendingCard: Card): Int
}
