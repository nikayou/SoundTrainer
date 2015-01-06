package soundTrainer


/*
 * A chord is a set of notes played at the same time.
 * The name of the chord should be given in international notation.
 * The notes are given as a list, because one player could want to add a delay
 * between two notes of the same chord, so they need to be always played in
 * the same order. 
 */
// TODO: a chord is different from one instrument to an other
class Chord [N] (private val _name: String, private val _notes: List[N]) 
{
 type Note = N
  override def toString = _name + ": " + (_notes map (_.toString) reduceLeft(_+"->"+_))
  def name : String = _name
  def notes : List[N] = _notes
}

object Chord 
{

}
