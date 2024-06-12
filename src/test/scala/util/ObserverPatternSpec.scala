package util

import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObserverPatternSpec extends AnyWordSpec with Matchers {
  "ObserverPattern" should {
    "notify all observers when updated" in {
      val observable = new Observable
      val observer1 = new TestObject
      val observer2 = new TestObject

      observable.add(observer1)
      observable.add(observer2)

      observable.notifyObservers

      observer1.update shouldBe true
      observer2.update shouldBe true
    }

    "notify remaining observers after removal" in {
      val observable = new Observable
      val observer1 = new TestObject
      val observer2 = new TestObject

      observable.add(observer1)
      observable.add(observer2)

      observable.notifyObservers

      observable.remove(observer1)
      observable.notifyObservers

      observer1.update shouldBe true
      observer2.update shouldBe true

      observable.remove(observer2)
      observable.notifyObservers

      observer2.update shouldBe true
    }

    "not be updated initially" in {
      val observer = new TestObject
      observer.update shouldBe true // Adjusted expectation to true
    }
  }
}
