package soundTrainer

import scala.util.Random

// events received by the controller
sealed abstract class InEvt extends swing.event.Event
case class ChangeNoteEvt (show: Boolean, play: Boolean) extends InEvt
case class ChangeChordEvt (show: Boolean, play: Boolean) extends InEvt
case class Show (show: Boolean) extends InEvt
case object Play extends InEvt

// events sent by the controller
sealed abstract class OutEvt extends swing.event.Event
case class ChangedNote extends OutEvt
case class ChangedChord extends OutEvt
case class ShowName(n: String) extends OutEvt
case object Hide extends OutEvt

class Controller extends AppController 
with Observer[InEvt] 
with Observable[OutEvt]
{
  type Note = String
  private val xml = new XMLChordLoader
  // TODO: don't load a specific instrument but a general database
  val chordsHolder = ChordHolder create (xml loadFromXML("res/chords.xml"))
  val player : Player = new MidiPlayer

  // TODO: preferences and chords filter

  // changes the current note for a random note
  def newNote : Note = { 
    val note = player.notes(Random.nextInt (player.notes.length))
    currentNote = Some(note)
    note
  }

  def newChord : Chord[_] = { 
    val possibleChords = chordsHolder.kept // TODO: inter preferences' filters
    val chord = possibleChords(Random.nextInt (possibleChords.length))
    currentChord = Some(chord)
    chord
  }

  
  def playCurrentNote = currentChord match {
    case Some(c) => player play (c, 0)
    case _ => println("no current chord") // TODO: errlog
  }

  override def receive (e: InEvt) = e match {
    case ChangeNoteEvt(show, play) => { 
      val n = newNote
      if (play) player play (n, 0)
      if (show) publish(ShowName(n.toString)) else publish(Hide)
    }
    case ChangeChordEvt(show, play) => { 
      val c = newChord
      if (play) player play (c, 0)
      if (show) publish(ShowName(c.name)) else publish(Hide)
    }
    case Show (show) => if (!show) publish(Hide) else {
      currentNote match {
	case Some(n) => publish(ShowName(n.toString))
	case None => currentChord match {
	  case Some(c) => publish(ShowName(c.name))
	  case None => publish(ShowName("?"))
	}
      }
    }
    case Play => currentNote match {
	case Some(n) => player play (n, 0)
	case None => currentChord match {
	  case Some(c) => player play (c, 0)
	  case None => 
	}
      }
  }

}

