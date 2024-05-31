package model
import model._

object ComparisonStrategyFactory {
  def createStrategy(strategyType: String): model.ComparisonStrategy = strategyType match {
    case "standard" => CardComparisonStrategies.StandardComparison
    case _ => CardComparisonStrategies.StandardComparison
  }
}