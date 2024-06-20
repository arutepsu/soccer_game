package model.CardComponent

import model.*

import scala.math.Ordered.orderingToOrdered

object CardComparisonStrategies {
  object StandardComparison extends ComparisonStrategy {
    override def compare(attackingCard: Card, defendingCard: Card): Int = {
      attackingCard.value.compare(defendingCard.value)
    }
  }
}