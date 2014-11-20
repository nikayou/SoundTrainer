package soundTrainer;

import javax.sound.midi._;

object SoundTrainer {

  def main(args: Array[String]) : Unit = {
/*    var e4 = new Note("E4")
    var c3 = new Note("C4")
    var g3 = new Note("G3")
    var e3 = new Note("E3")
    var c2 = new Note("C3")
    var b2 = new Note("B3") 
    var l1 = List(c2, e3, g3, c3, e4)
    var l2 = List(b2, e3)
    var c = new Chord ("C", l1)
    var d = new Chord ("E", l2)
  */  var cl = new XMLChordLoader;
    cl loadFromXML("guitarChords.xml");
    var p : Player = new MidiPlayer
  }
}
