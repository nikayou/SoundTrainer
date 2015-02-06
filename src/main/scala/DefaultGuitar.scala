package soundTrainer 

import scala.{swing => sw}

abstract class DefaultGuitar (midiPlayer: MidiPlayer) extends Instrument {

  class Panel extends sw.Panel {

    def display (c: Chord[_]) = println("display")
    def hide = println("hide")
  }

  override val player : Option[MidiPlayer] = Some(midiPlayer)
  override val name = "Guitar"
  val panel = new Panel
  override val chordHolder = ChordHolder.create(XMLChordLoader.loadFromXML("guitarChords.xml"))
  
}

