package util

class ObserverPattern {
  val observable = new Observable
  val observer1 = new TestObject
  val observer2 = new TestObject

  def initializeObservers(): Unit = {
    observable.add(observer1)
    observable.add(observer2)
  }

  def notifyAllObservers(): Unit = {
    observable.notifyObservers()
  }

  def removeObserver(observer: TestObject): Unit = {
    observable.remove(observer)
  }
}
