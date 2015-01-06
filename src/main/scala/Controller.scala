package soundTrainer

import scala.util.Random


object Controller extends AppController
{
  type Note = String
  private val xml = new XMLChordLoader
  // TODO: don't load a specific instrument but a general database
  val chordsHolder = ChordHolder create (xml loadFromXML("res/guitarChords.xml"))
  val player : Player = new MidiPlayer

  // TODO: preferences and chords filter

  // changes the note for a random note
  def changeNote = { 
    val possibleChords = chordsHolder.chords // TODO: inter preferences' filters
    val chord = possibleChords(Random.nextInt (possibleChords.length))
    currentChord = Some(chord)
    if (mode == ModeSound()) //TODO: incorrect
      player play (chord, 0) // TODO: remove, according to the mode
  }
}

