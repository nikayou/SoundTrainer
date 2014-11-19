package soundTrainer;

import javax.sound.midi._;

object SoundTrainer {

  def main(args: Array[String]) : Unit = {
    var e4 = new Note("E4")
    var c3 = new Note("C3")
    var g3 = new Note("G3")
    var e3 = new Note("E3")
    var c2 = new Note("C2")
    var b2 = new Note("B2") 
    var l1 = List(c2, e3, g3, c3, e4)
    var l2 = List(b2, e3)
    var c = new Chord ("C", l1)
    var d = new Chord ("E", l2)
    var p : Player = new MidiPlayer
    p.play (c, 0);
    Thread.sleep(500);
  }
}
