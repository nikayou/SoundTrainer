package soundTrainer 

import scala.{swing => sw}
import javax.swing.ImageIcon

class DefaultPiano (_player: Player) extends Instrument {

  class Panel extends sw.FlowPanel {

    // the piano keyboard
    val image = new sw.Label { icon = new ImageIcon("res/keyboard.png") }
    contents += image

    def display (c: Chord[_]) = println(s"displaying piano $c")
    def display (name: String) = println(s"displaing $name")
    def hide = println("hide")

    
  }

  override val player : Option[Player] = Some(_player)
  override val name = "Piano"
  val panel = new Panel
  override val chordHolder = ChordHolder.create(XMLChordLoader.loadFromXML("res/pianoChords.xml"))
  def receive (e: OutEvt) = e match {
    case ShowName (n) => panel.display(n)
    case _ =>
  }

}

