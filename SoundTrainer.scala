package soundTrainer;

import javax.sound.midi._;

object SoundTrainer {

  def main(args: Array[String]) : Unit = {
    var cl = new XMLChordLoader;
    cl loadFromXML("guitarChords.xml");
    var p : Player = new MidiPlayer
  }
}
