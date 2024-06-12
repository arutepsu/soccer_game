package util
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers



class CommandSpec extends AnyWordSpec with Matchers {
  "A Command" should {

    "have a do step" in {
      val command = new incrCommand
      command.state.shouldBe(0)
      command.doStep
      command.state.shouldBe(1)
      command.doStep
      command.state.shouldBe(2)

    }
    "have an undo step" in {
      val command = new incrCommand
      command.state.shouldBe(0)
      command.doStep
      command.state.shouldBe(1)
      command.undoStep
      command.state.shouldBe(0)
    }
    "have a redo step" in {
      val command = new incrCommand
      command.state.shouldBe(0)
      command.doStep
      command.state.shouldBe(1)
      command.undoStep
      command.state.shouldBe(0)
      command.redoStep
      command.state.shouldBe(1)
    }
  }
}
