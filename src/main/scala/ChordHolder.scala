package soundTrainer

import scala.{swing => sw}

class ChordHolder (val chords: Array[Chord[_]]) 
{ 
  var _kept = chords
  def kept = _kept
  def keep(c: Array[Chord[_]]) = { _kept = c }
  
  val _dialog = new sw.Dialog {
    title = "Chords selection"
    contents = new sw.FlowPanel {
      contents += new sw.Label("Chords selection")
    }
  }
  def dialog = _dialog
}

object ChordHolder 
{
  def create (a : Array[Chord[_]]) = new ChordHolder(a)
  def create (l : List[Chord[_]]) = new ChordHolder(l toArray)
}

