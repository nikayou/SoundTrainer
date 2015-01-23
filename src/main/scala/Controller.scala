package soundTrainer

import scala.util.Random


class Controller extends AppController
{
  type Note = String
  private val xml = new XMLChordLoader
  // TODO: don't load a specific instrument but a general database
  val chordsHolder = ChordHolder create (xml loadFromXML("res/chords.xml"))
  val player : Player = new MidiPlayer

  // TODO: preferences and chords filter

  // changes the note for a random note
  def changeNote = { 
    val possibleChords = chordsHolder.kept // TODO: inter preferences' filters
    val chord = possibleChords(Random.nextInt (possibleChords.length))
    currentChord = Some(chord)
    if (mode == ModeSound())
      player play (chord, 0)
  }
  
  def playCurrentNote = currentChord match {
    case Some(c) => player play (c, 0)
    case _ => println("no current chord") // TODO: errlog
  }

  
  
}

