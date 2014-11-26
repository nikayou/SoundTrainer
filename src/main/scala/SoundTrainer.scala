package soundTrainer;

import javax.sound.midi._;

object SoundTrainer {

  def main(args: Array[String]) : Unit = {
    var cl = new XMLChordLoader;
    var ch = ChordHolder create(cl loadFromXML("guitarChords.xml"));
    var p : Player = new MidiPlayer    
    for (c <- ch.chords) {
      println(c)
      p play (c, 0)
      Thread.sleep(500);
    }
  }
}
