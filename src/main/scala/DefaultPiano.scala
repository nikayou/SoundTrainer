package soundTrainer 

import scala.{swing => sw}
import javax.imageio.ImageIO
import java.io.File

class DefaultPiano (_player: Player) extends Instrument {

  class Panel extends sw.FlowPanel {

    private var showDots = false
    private var redDots : List[Int] = Nil
    // the piano keyboard
    val image = ImageIO.read(new File("res/keyboard.png"))
    preferredSize = new sw.Dimension(image.getWidth, image.getHeight)
    minimumSize = preferredSize

    def display (c: Chord[_]) = 
      { 
	showDots = true 
	println(s"displaying piano $c") 
      }
    def display (name: String) = 
      { 
	showDots = true 
	redDots = List(0, 5, 12)
	repaint
      }
    def hide = { 
      showDots = false 
      redDots = Nil
      repaint
    }

    private val dots_y = 60
    private val dots_gap = 19
    private val dots_x = 9
    private val dots_radius = 16
    override def paintComponent (g: sw.Graphics2D) = 
      {
	println("painting")
	g.drawImage(image, 0, 0, null)
	g.setColor(java.awt.Color.RED)
	for (dot <- redDots) {
	  val x = dots_x+(dots_gap*dot) - dots_radius/2
	  val y = dots_y
	  println(s"painting dot $dot at $x,$y")
	  g.fillOval (x, y, dots_radius, dots_radius)
	}
      }
  }

  override val player : Option[Player] = Some(_player)
  override val name = "Piano"
  val panel = new Panel
  override val chordHolder = ChordHolder.create(XMLChordLoader.loadFromXML("res/pianoChords.xml"))
  def receive (e: OutEvt) = e match {
    case ShowName (n) => panel.display(n)
    case Hide => panel hide
    case _ =>
  }

}

