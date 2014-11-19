package soundTrainer;

/*
 * A chord is a set of notes played at the same time.
 * The name of the chord should be given in international notation.
 * The notes are given as a list, because one player could want to add a delay
 * between two notes of the same chord, so they need to be always played in
 * the same order. 
 **/

class Chord (val name: String, val _notes: List[Note]) {
  override def toString = name + (_notes map (_ toString) reduceLeft(_+"->"+_))
  def notes : List[Note] = _notes;
}

object Chord {

}
