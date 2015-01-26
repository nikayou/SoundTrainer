package soundTrainer

import scala.{swing => sw}

class PlayPanel(val controller: Controller) extends sw.FlowPanel()
with Observer[OutEvt]
with Observable[InEvt]
{
  observe(controller)
  override def receive (e: OutEvt) = e match {
    case ShowName(n) => nameLabel.text = n
    case Hide => nameLabel.text = " "
    case ChangedMode (t) => if (t) changeModeName("Chord")
			    else changeModeName("Note")
  }

  private def changeModeName (s: String) = {
    modeLabel.text = s 
    modeLabel.repaint
    changeButton.text = "Mode: \n"+s;
  }

  private val nameLabel = new sw.Label(" ") {
    foreground = UISkin.labelForeground
  }
  private val modeLabel = new sw.Label("Note"); 
  private val changeButton = new sw.Button {
    action = sw.Action("Switch Mode") {
      publishTo(ChangeMode, controller)
    }
  }
  private def reveal = {
    controller.currentChord match {
      case None => nameLabel.text = "?"
      case Some(c) => nameLabel.text = c.name
    }
  }

  private val newGroup = new sw.GridPanel(1, 2) {
    contents += new sw.FlowPanel {
      contents += changeButton
//      layout(changeButton) = sw.BorderPanel.Position.West;
//      layout(modeLabel) = sw.BorderPanel.Position.Center;
    }
    contents +=  new sw.GridPanel(2,1) {
      contents += new sw.Button {
	action = sw.Action("Sound") {
	  publishTo(Change(false, true), controller)
	}
      }
      contents += new sw.Button {
	action = sw.Action("Name") {
	  publishTo(Change(true, false), controller)
	}
      }
    }
  }
  private val noteGroup = new sw.BorderPanel() {
    val noteZone = new sw.FlowPanel() {
      background = UISkin.labelBackground
      contents += nameLabel
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
