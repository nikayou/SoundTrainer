package soundTrainer;

import javax.sound.midi._;

object SoundTrainer {

  def main(args: Array[String]) : Unit = {
    var cl = new XMLChordLoader;
    var ch = ChordHolder create(cl loadFromXML("guitarChords.xml"));
    for (c <- ch.chords)
      println(c)
    var p : Player = new MidiPlayer
  }
}
