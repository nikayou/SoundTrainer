package soundTrainer

import scala.{swing => sw}


abstract class Instrument
{

  val name = "Instrument"
  val configDialog : sw.Dialog
  val panel : sw.Panel

}

