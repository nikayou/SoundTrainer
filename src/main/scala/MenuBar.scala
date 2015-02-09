package soundTrainer

import scala.{swing => sw}

class MenuBar (val controller: Controller) extends sw.MenuBar
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

  contents += new sw.Menu("New") {
    contents += new sw.MenuItem (new sw.Action("Name") {
      def apply = publishTo(Change(true, false), controller)
    })
    contents += new sw.MenuItem (new sw.Action("Sound") {
      def apply = publishTo(Change(false, true), controller)
    })
  }
  contents += new sw.Menu("Reveal") {
    contents += new sw.MenuItem (new sw.Action("Name") {
      def apply = publishTo(Show(true), controller)
    })
    contents += new sw.MenuItem (new sw.Action("Sound") {
      def apply = publishTo(Play, controller)
    })
  }
  contents += new sw.Menu("Instruments") {
    println("loading instruments");
    for (i <- controller.instruments) {
      println("insturment:"+i.name);
      contents += new sw.MenuItem (new sw.Action(i.name) {
	def apply = (new sw.Dialog {contents=i.panel; resizable=false}) open
      })
    }
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

