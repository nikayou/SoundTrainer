package soundTrainer


/*
 * A chord is a set of notes played at the same time.
 * The name of the chord should be given in international notation.
 * The notes are given as a list, because one player could want to add a delay
 * between two notes of the same chord, so they need to be always played in
 * the same order. 
 */
class Chord [N] (private val _name: String, private val _notes: List[N]) 
{
 type Note = N
  override def toString = if (_notes.length > 1) {
    _name + ": " + (_notes map (_.toString) reduceLeft(_+"->"+_))
  } else {
    _name
  }
  def name : String = _name
  def notes : List[N] = _notes
}

object Chord 
{

}
