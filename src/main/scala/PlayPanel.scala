package soundTrainer

import scala.{swing => sw}

object PlayPanel extends sw.BoxPanel(sw.Orientation.Horizontal)
{
  val controller = Controller // TODO: use as member, not singleton
  val label = new sw.Label(" ") {
    //    opaque = true
    foreground = UISkin.labelForeground
  }

  contents += new sw.GridBagPanel {
    val zeroInsets = new sw.Insets(0,0,0,0)
    def resetAt(c: Constraints, x: Int, y: Int) = {
      c.fill = sw.GridBagPanel.Fill.Horizontal
      c.weightx = 1
      c.weighty = 1
      c.ipadx = 0
      c.ipady = 0
      c.gridwidth = 1
      c.gridheight = 1
      c.insets = zeroInsets
      c.gridx = x
      c.gridy = y
    }
    private val c = new Constraints
    val soundButton = new sw.Button {
      action = sw.Action("Sound") {
	controller changeMode (ModeSound())
	controller changeNote;
	label.text = ""
      }
    }
    val noteButton = new sw.Button {
      action = sw.Action("Note") {
	controller changeMode (ModeNote())
	controller changeNote;
	reveal
      }
    }
    val playButton = new sw.Button {
      action = sw.Action("♫") {
	controller playCurrentNote
      }
    }
    val revealButton = new sw.Button {
      action = sw.Action("^") { reveal }
    }
    def reveal = {
      controller.currentChord match {
	case None => label.text = "?"
	case Some(c) => label.text = c.name
      }
    }
    c.gridx = 0
    c.gridy = 0
    layout(soundButton) = c
    c.gridy = 1
    layout(noteButton) = c
    c.gridx = 1
    c.gridy = 1
    c.anchor = sw.GridBagPanel.Anchor.LineEnd
    layout(playButton) = c
    c.gridx = 2
    c.anchor = sw.GridBagPanel.Anchor.LineStart
    layout(revealButton) = c
    c.gridx = 1
    c.gridy = 0
    c.gridwidth = 2
    c.weightx = 1
    c.anchor = sw.GridBagPanel.Anchor.Center
    layout(new sw.FlowPanel {
      background = UISkin.labelBackground
      contents += label
    }) = c
  }
}
