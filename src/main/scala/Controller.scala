package soundTrainer

import scala.util.Random

// events received by the controller
sealed abstract class InEvt extends swing.event.Event
case class Change (show: Boolean, play: Boolean) extends InEvt
case class Show (show: Boolean) extends InEvt
case object ModeNote extends InEvt
case object ModeChord extends InEvt
case object ChangeMode extends InEvt
case object Play extends InEvt
case class PlayWith (instrument: Int) extends InEvt

// events sent by the controller
sealed abstract class OutEvt extends swing.event.Event
case class ChangedMode(chord: Boolean) extends OutEvt
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
  private var modeChord : Boolean = true; // true if playing chords, false for notes
  // TODO: preferences and chords filter

  // changes the current note for a random note
  private def newNote : Chord[_] = { 
    val note = player.notes(Random.nextInt (player.notes.length))
    val chord = new Chord(note, List(note))
    currentChord = Some(chord)
    chord
  }

  private def newChord : Chord[_] = { 
    val possibleChords = chordsHolder.kept // TODO: inter preferences' filters
    val chord = possibleChords(Random.nextInt (possibleChords.length))
    currentChord = Some(chord)
    chord
  }

  
  private def playCurrentNote = currentChord match {
    case Some(c) => player play (c)
    case _ => println("no current chord") // TODO: errlog
  }

  override def receive (e: InEvt) = e match {
    case ChangeMode => { changeMode(!modeChord) }
    case ModeChord => { changeMode(true) }
    case ModeNote => { changeMode(false) }
    case Change(show, play) => { 
      val c : Chord[_] = if (modeChord) newChord else newNote
      if (show) publish(ShowName(c.name)) else publish(Hide)
      if (play) player play (c)
    }
    case Show (show) => if (!show) publish(Hide) else {
      currentChord match {
	case Some(c) => publish(ShowName(c.name))
	case None => publish(ShowName("?"))
      }
    }
    
    case Play => currentChord match {
      case Some(c) => player play (c)
      case None => 
    }
    case PlayWith (i) => currentChord match {
      case Some(c) => player.play(c, i)
      case None => 
    }

  }

    private def changeMode (chord: Boolean) = {
      modeChord = chord
      publish(ChangedMode(chord))
    }

}
