package soundTrainer

import scala.{swing => sw}


abstract class MenuBar extends sw.MenuBar 
{
  // TODO: all members should be val
  var playerDialog : Option[sw.Dialog] = None
  var chordsDialog : Option[sw.Dialog] = None
  var skinDialog : Option[sw.Dialog] = None
  var instrumentsPlugins : List[Instrument] = Nil
  var controller : Controller = new Controller
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
	contents += new sw.MenuItem("Chords") {
	  action = new sw.Action("Chords_") {
	    def apply = d open
	  }}
      case None => 
    }
    playerDialog match {
      case Some(d) =>
	contents += new sw.MenuItem("Player") {
	  action = new sw.Action("Player_") {
	    def apply = d open
	  }
	}
      case None => 
    }
    skinDialog match {
      case Some(d) => 
	contents += new sw.MenuItem("Skin") {
	  action = new sw.Action("Skin_") {
	    def apply = d open
	  }}
      case None => 
    }
    
  }
}

