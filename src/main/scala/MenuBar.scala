package soundTrainer

import scala.{swing => sw}

class MenuBar(val controller: Controller) extends sw.MenuBar
with Observable[InEvt]
{
  controller.observe(this)
  // TODO: all members should be val
  val playerDialog : Option[sw.Dialog] = 
    controller.player.preferences match {
      case None => None
      case Some(p) => Some(p.dialog)
    }
  var chordsDialog : sw.Dialog = controller.chordsHolder.dialog
  var skinDialog : Option[sw.Dialog] = None
  var instrumentsPlugins : List[Instrument] = Nil
  contents += new sw.Menu("New") {
    contents += new sw.MenuItem (new sw.Action("Note") {
      def apply = controller.changeNote
      publishTo(InEvt(32), controller)
    })
    contents += new sw.MenuItem (new sw.Action("Sound") {
      def apply = controller.changeNote
    })
  }
  contents += new sw.Menu("Reveal") {
    contents += new sw.MenuItem (new sw.Action("Note") {
      def apply = println("revealing note")
    })
    contents += new sw.MenuItem (new sw.Action("Sound") {
      def apply = println("revealing sound")
    })
  }
  contents += new sw.Menu("Instruments") {
    //TODO: load plugins
  }
  contents += new sw.Menu("Preferences") {
    contents += new sw.MenuItem(new sw.Action("Chords") {
      def apply = chordsDialog open
    })
    playerDialog match {
      case Some(d) =>
	contents += new sw.MenuItem(new sw.Action("Player") {
	    def apply = d open
	  })
      case None => 
    }
    skinDialog match {
      case Some(d) => 
	contents += new sw.MenuItem(new sw.Action("Skin") {
	    def apply = d open
	  })
      case None => 
    }
    
  }
}

