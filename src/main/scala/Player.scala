package soundTrainer

import scala.{swing => sw}

abstract class Player 
{
  def play (c : Chord[_], instrument : Int) : Unit
  def play (c : Chord[_]) : Unit = play(c, 0)
/*  def play (n: String, instrument : Int) : Unit
  def play (n: String) : Unit*/
  def preferences : Option[Preferences] = None
  val notes : Seq[String] = Nil
}

