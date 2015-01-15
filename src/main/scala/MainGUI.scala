package soundTrainer

import scala.{swing => sw}


object MainGUI extends sw.SimpleSwingApplication 
{

  def top = new sw.MainFrame {
    title = "Sound Trainer"
    contents = PlayPanel
    menuBar = MenuBar
    pack()
  }

  override def startup (args: Array[String]) = println("start")
  override def quit = println ("quit")
  override def shutdown = println("shutdown")
  
  top open
}
