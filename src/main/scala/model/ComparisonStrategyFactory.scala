package model
import model.*
import model.CardComponent.{CardComparisonStrategies, ComparisonStrategy}

object ComparisonStrategyFactory {
  def createStrategy(strategyType: String): ComparisonStrategy = strategyType match {
    case "standard" => CardComparisonStrategies.StandardComparison
    case _ => CardComparisonStrategies.StandardComparison
  }
}