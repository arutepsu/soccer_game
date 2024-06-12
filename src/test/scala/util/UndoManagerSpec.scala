package util
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class UndoManagerSpec extends AnyWordSpec with Matchers {

  "An UndoManager" should {
    val undoManager = new UndoManager

    "have a do, undo and redo" in {
      val command = new incrCommand
      command.state.shouldBe(0)
      undoManager.doStep(command)
      command.state.shouldBe(1)
      undoManager.undoStep
      command.state.shouldBe(0)
      undoManager.redoStep
      command.state.shouldBe(1)
    }

    "handle multiple undo steps correctly" in {
      val command = new incrCommand
      command.state.shouldBe(0)
      undoManager.doStep(command)
      command.state.shouldBe(1)
      undoManager.doStep(command)
      command.state.shouldBe(2)
      undoManager.undoStep
      command.state.shouldBe(1)
      undoManager.undoStep
      command.state.shouldBe(0)
      undoManager.redoStep
      command.state.shouldBe(1)
    }
  }
}