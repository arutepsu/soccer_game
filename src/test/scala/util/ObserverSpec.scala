package util

import org.scalatest.wordspec.AnyWordSpec

class ObserverSpec extends AnyWordSpec {

  "An Observer" should {

    "return the correct updated status" in {
      val observer = new Observer {
        override def update: Boolean = {
          updated = true
          updated
        }
      }
      assert(!observer.isUpdated)
      observer.update
      assert(observer.isUpdated)
    }

    "correctly implement the update method" in {
      val observer = new Observer {
        override def update: Boolean = {
          updated = true
          updated
        }
      }
      assert(observer.update)
      assert(observer.isUpdated)
    }
  }

  "An Observable" should {

    "allow adding observers" in {
      val observable = new Observable
      val observer = new Observer {
        override def update: Boolean = {
          updated = true
          updated
        }
      }
      assert(observable.subscribers.isEmpty)
      observable.add(observer)
      assert(observable.subscribers.contains(observer))
    }

    "allow removing observers" in {
      val observable = new Observable
      val observer = new Observer {
        override def update: Boolean = {
          updated = true
          updated
        }
      }
      observable.add(observer)
      assert(observable.subscribers.contains(observer))
      observable.remove(observer)
      assert(!observable.subscribers.contains(observer))
    }

    "notify all observers" in {
      val observable = new Observable
      val observer1 = new Observer {
        override def update: Boolean = {
          updated = true
          updated
        }
      }
      val observer2 = new Observer {
        override def update: Boolean = {
          updated = true
          updated
        }
      }
      observable.add(observer1)
      observable.add(observer2)
      observable.notifyObservers()
      assert(observer1.isUpdated)
      assert(observer2.isUpdated)
    }
  }
}
