package soundTrainer

trait AppController
{
  type Note
  val chordsHolder : ChordHolder
  val player : Player
  var currentChord : Option[Chord[_]] = None
  var currentNote : Option[Note] = None
}
