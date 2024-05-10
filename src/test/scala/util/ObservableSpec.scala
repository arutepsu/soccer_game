package util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyWordSpec with Matchers {
  "An Observable" should {
    val observable = new Observable
    val observer = new Observer {

      override def update: Boolean = { true }
    }
    "add an Observer" in {
      observable.add(observer)
      observable.subscribers should contain(observer)
    }
    "notify an Observer" in {
//      observer.isUpdated should be(false)
//      observer.update
      observable.notifyObservers
//      observer.isUpdated should be(true)
      observer.update

    }
    "remove an Observer" in {
      observable.remove(observer)
      observable.subscribers should not contain(observer)
    }
  }
}
