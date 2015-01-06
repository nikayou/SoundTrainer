package soundTrainer;

class ChordHolder(val chords: Array[Chord[_]]) { 
}

object ChordHolder {
  def create (a : Array[Chord[_]]) = new ChordHolder(a)
  def create (l : List[Chord[_]]) = new ChordHolder(l toArray)
}

