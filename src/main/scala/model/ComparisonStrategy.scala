package model
import model._

trait ComparisonStrategy {
  def compare(attackingCard: Card, defendingCard: Card): Int
}
