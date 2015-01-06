package soundTrainer

trait SoundTrainer {
//  type Note
  val chordsHolder : ChordHolder
  val player : Player
  var currentChord : Option[Chord[_]] = None
}
