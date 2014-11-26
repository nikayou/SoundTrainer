package soundTrainer;

/**
 * A note is a special Chord with only one Note. The concept is separated
 * because users could want to work with individual notes. 
 **/
class Note (override val name: String) extends Chord[String] (name, List(name)) {
  override def toString = name;
}
