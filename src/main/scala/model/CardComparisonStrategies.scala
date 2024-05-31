package model
import model._
import scala.math.Ordered.orderingToOrdered

object CardComparisonStrategies {
  object StandardComparison extends model.ComparisonStrategy {
    override def compare(attackingCard: Card, defendingCard: Card): Int = {
      attackingCard.value.compare(defendingCard.value)
    }
  }
}