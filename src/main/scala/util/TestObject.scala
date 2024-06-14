package util

class TestObject extends Observer {
  override def update: Boolean = {
    updated = true
    updated
  }
}