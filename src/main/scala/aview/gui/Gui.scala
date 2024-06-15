package aview.gui

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import controller.Controller

class Gui(controller: Controller) extends JFXApp3 {
  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "First GUI"
      // Additional GUI setup goes here
    }
  }
}
