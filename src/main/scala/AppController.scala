package soundTrainer

sealed class AppMode
case class ModeNote() extends AppMode
case class ModeSound() extends AppMode

trait AppController
{
//  type Note
  val chordsHolder : ChordHolder
  val player : Player
  var currentChord : Option[Chord[_]] = None
  var mode : AppMode = new ModeSound
}
