package soundTrainer

import scala.{swing => sw}


object MainGUI extends sw.SimpleSwingApplication 
{
  private val controller = new Controller

  def top = new sw.MainFrame {
    title = "Sound Trainer"
    contents = new PlayPanel(controller)
    menuBar = new MenuBar(controller)
    pack()
  }

  override def startup (args: Array[String]) = println("start")
  override def quit = println ("quit")
  override def shutdown = println("shutdown")
  
  top open
}
