package soundTrainer

import scala.{swing => sw}


abstract class MenuBar(val controller: Controller) extends sw.MenuBar 
{
  // TODO: all members should be val
  var playerDialog : Option[sw.Dialog] = 
    controller.player.preferences match {
      case None => None
      case Some(p) => Some(p.dialog)
    }
  var chordsDialog : Option[sw.Dialog] = None
  var skinDialog : Option[sw.Dialog] = None
  var instrumentsPlugins : List[Instrument] = Nil
  contents += new sw.Menu("New") {
    contents += new sw.MenuItem ("Note")
    contents += new sw.MenuItem ("Sound")
  }
  contents += new sw.Menu("Reveal") {
    contents += new sw.MenuItem ("Name")
    contents += new sw.MenuItem ("Sound")
  }
  contents += new sw.Menu("Instruments") {
    //TODO: load plugins
  }
  contents += new sw.Menu("Preferences") {
    chordsDialog match {
      case Some(d) =>
	contents += new sw.MenuItem(new sw.Action("Chords") {
	    def apply = d open
	  })
      case None => 
    }
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
