package soundTrainer

import scala.{swing => sw}

object PlayPanel extends sw.BoxPanel(sw.Orientation.Horizontal)
{
  contents += new sw.BoxPanel(sw.Orientation.Vertical) {
    contents += new sw.Button("Sound"){
      action = sw.Action("lol") {
	Controller changeNote //TODO: use as member, not global
      }
    }
    contents += new sw.Button("Note")
  }
}
