package soundTrainer

import scala.{swing => sw}

class PlayPanel(val controller: Controller) extends sw.FlowPanel()
with Observer[OutEvt]
with Observable[InEvt]
{
  observe(controller)
  override def receive (e: OutEvt) = e match {
	case ShowName(n) => label.text = n.toString
	  case Hide => label.text = " "
  }

  val label = new sw.Label(" ") {
    foreground = UISkin.labelForeground
  }
  def reveal = {
    controller.currentChord match {
      case None => label.text = "?"
      case Some(c) => label.text = c.name
    }
  }

//  val newGroup = new sw.BoxPanel(sw.Orientation.Vertical) {
  val newGroup = new sw.GridPanel(2, 1) {
    contents += new sw.Button {
      action = sw.Action("Sound") {
	publishTo(ChangeNoteEvt(false, true), controller)
      }
    }
    contents += new sw.Button {
      action = sw.Action("Note") {
	publishTo(ChangeNoteEvt(true, false), controller)
      }
    }
  }
  val noteGroup = new sw.BorderPanel() {
    val noteZone = new sw.FlowPanel() {
	background = UISkin.labelBackground
	contents += label
    }
    val buttonZone = new sw.BorderPanel() {
      val playButton = new sw.Button {
	action = sw.Action("♫") {
	  publishTo(Play, controller)
	}
      }
      val revealButton = new sw.Button {
	action = sw.Action("^") { 
	  publishTo(Show(true), controller) 
	}
      }
      layout(playButton) = sw.BorderPanel.Position.West
      layout(revealButton) = sw.BorderPanel.Position.East
    }
    layout(noteZone) = sw.BorderPanel.Position.North
    layout(buttonZone) = sw.BorderPanel.Position.South    
  }
//  layout(newGroup) = sw.BorderPanel.Position.West
//  layout(noteGroup) = sw.BorderPanel.Position.Center
  contents += newGroup
  contents += noteGroup

}
