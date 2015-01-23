package soundTrainer

import scala.{swing => sw}

abstract class Player 
{
  def play (c : Chord[_], instrument : Int)
  def play (n: String, instrument : Int)
  def preferences : Option[Preferences] = None
  val notes : Seq[String] = Nil
}

