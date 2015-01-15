package soundTrainer

import scala.{swing => sw}

object PlayPanel extends sw.FlowPanel()
{
  val controller = Controller // TODO: use as member, not singleton
  val label = new sw.Label(" ") {
    //    opaque = true
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
	controller changeMode (ModeSound())
	controller changeNote;
	label.text = ""
      }
    }
    contents += new sw.Button {
      action = sw.Action("Note") {
	controller changeMode (ModeNote())
	controller changeNote;
	reveal
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
	  controller playCurrentNote
	}
      }
      val revealButton = new sw.Button {
	action = sw.Action("^") { reveal }
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
