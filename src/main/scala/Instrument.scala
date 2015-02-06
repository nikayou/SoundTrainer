package soundTrainer

import scala.{swing => sw}


abstract class Instrument
{

  abstract class Panel extends sw.Panel {
    // the minimum required is simply to be able to display a note/chord
    def display (c: Chord[_])
    def hide
  }

  // a name is required, to display it in the menu
  val name = "Instrument"
  // a panel is required to display it 
  val panel : sw.Panel
  // a chord holder is required, to know how a chord can be played
  val chordHolder : ChordHolder
  // a config dialog could be required for settings options
  val configDialog : Option[sw.Dialog] = None
  // a specific player can be provided, if the sound is different
  val player : Option[Player] = None

}

