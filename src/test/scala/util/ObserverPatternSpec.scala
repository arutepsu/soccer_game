package util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObserverPatternSpec extends AnyWordSpec with Matchers {

  "An ObserverPattern" should {
    "initialize and notify observers correctly" in {
      val pattern = new ObserverPattern
      pattern.initializeObservers()

      pattern.notifyAllObservers()

      pattern.observer1.isUpdated shouldBe true
      pattern.observer2.isUpdated shouldBe true
    }

    "remove and notify observers correctly" in {
      val pattern = new ObserverPattern
      pattern.initializeObservers()

      pattern.notifyAllObservers()

      pattern.observable.remove(pattern.observer1)
      pattern.observer1.updated = false

      pattern.notifyAllObservers()

      pattern.observer1.isUpdated shouldBe false
      pattern.observer2.isUpdated shouldBe true
    }
  }

  "An Observable" should {
    "notify all observers when updated" in {
      val observable = new Observable
      val observer1 = new TestObject
      val observer2 = new TestObject

      observable.add(observer1)
      observable.add(observer2)

      observable.notifyObservers()

      observer1.isUpdated shouldBe true
      observer2.isUpdated shouldBe true
    }

    "notify remaining observers after removal" in {
      val observable = new Observable
      val observer1 = new TestObject
      val observer2 = new TestObject

      observable.add(observer1)
      observable.add(observer2)

      observable.notifyObservers()

      observable.remove(observer1)
      observer1.updated = false

      observable.notifyObservers()

      observer1.isUpdated shouldBe false
      observer2.isUpdated shouldBe true
    }

    "not be updated initially" in {
      val observer = new TestObject
      observer.isUpdated shouldBe false
    }

    "handle adding and removing multiple observers correctly" in {
      val observable = new Observable
      val observer1 = new TestObject
      val observer2 = new TestObject
      val observer3 = new TestObject

      observable.add(observer1)
      observable.add(observer2)
      observable.add(observer3)

      observable.notifyObservers()

      observer1.isUpdated shouldBe true
      observer2.isUpdated shouldBe true
      observer3.isUpdated shouldBe true

      observer1.updated = false
      observer2.updated = false
      observer3.updated = false

      observable.remove(observer2)
      observable.notifyObservers()

      observer1.isUpdated shouldBe true
      observer2.isUpdated shouldBe false
      observer3.isUpdated shouldBe true
    }
  }
}
