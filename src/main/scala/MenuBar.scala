package soundTrainer

import scala.{swing => sw}


object MenuBar extends sw.MenuBar 
{
  contents += new sw.Menu("Sound") {
    contents += new sw.MenuItem ("Filter")
    contents += new sw.MenuItem ("Delay")
  }
}

