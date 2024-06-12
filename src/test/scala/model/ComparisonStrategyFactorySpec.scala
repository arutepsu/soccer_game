package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ComparisonStrategyFactorySpec extends AnyWordSpec with Matchers {

  "A ComparisonStrategyFactory" should {
    "create the correct strategy based on the input string" in {
      val strategy = ComparisonStrategyFactory.createStrategy("standard")
      strategy shouldBe CardComparisonStrategies.StandardComparison
    }

    "return the standard strategy if the input string is unknown" in {
      val strategy = ComparisonStrategyFactory.createStrategy("unknown")
      strategy shouldBe CardComparisonStrategies.StandardComparison
    }
  }
}
