package soundTrainer

import javax.sound.midi.MidiSystem
import javax.sound.midi.Synthesizer
import javax.sound.midi.MidiChannel
import javax.sound.midi.MidiDevice

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

import scala.{swing => sw}

case class SetInstrument(instrument: Int)
case class PlayChord(chord: Chord[_])
case object Stop
case object Init
case object Close

/*
 * MidiPlayer is one concrete class for Player that plays notes using a
 * midi sequencer. It holds a map [String->Int] whose keys are the name of the
 * note (in international notation) and the values are the midi code.
 * // TODO: 
 * - close sequencer when program ends
 * - check if notes and codes can varry with a different synthesizer
 * - handle exception with devices, threads and availability
 */
class MidiPlayer extends Player 
{

  val _preferences = new MidiPreferences
  override val preferences = Some(_preferences)

  class PlayServer extends Actor 
  {

    var playing: Boolean = false;

    def receive = {
      case Init => changeInstrument (_preferences.instrument)
      case SetInstrument(i) => changeInstrument (i)
      case Stop => stop
      case PlayChord(chord)
      =>  if (!playing) { playing = true
			 chord.notes foreach { 
			   x => {playNote(midiCodes(x.toString))
				 Thread.sleep(_preferences.delay)}}
			 Thread.sleep(_preferences.duration)
			 stop
			 }
      }

    private def playNote (n: Int) = {
      channels (0) noteOn (n, _preferences.volume)
    }

    private def stop = { channels(0).allNotesOff
			playing = false }

    private def changeInstrument (i: Int) = channels (0) programChange i

  }

  val actorSystem = ActorSystem("playSystem") //TODO: shutdown when leaving
  val playServer = actorSystem.actorOf(Props(new PlayServer()), name="playServer");

  val synthesizer : Synthesizer = 
    try { 
      MidiSystem.getSynthesizer();
    } catch {
      case mue: javax.sound.midi.MidiUnavailableException 
      => println("Midi device is unavailable: "+mue) // TODO: log
      null
      case e: Exception 
      => println("A problem occured while getting midi synthesizer: "+e)
      null
    }
  // TODO: also close when program ends
  if (synthesizer != null)
    synthesizer.open()
  val channels : Array[MidiChannel] = synthesizer.getChannels();

  val midiCodes : Map[String, Int] = 
    {
      val notes = List("C","C#","D","D#","E","F","F#","G","G#","A","A#","B")
      val maxPitch = 8 // TODO: can this varry with the sequencer?
      /* note:
      * pitch changes on the C, but first notes on the spectrum are A0, A#0, B0,
      * and the very last is C8. We need to cheat for those values.
      */
      // this var map will be injected in a val, so it won't be modifiable
      var map : Map[String, Int] = Map("A0"->21,"A#0"->22,"B0"->23)
      var index = 24;
      // computing all possible notes (except the last pitch: it contains only C)
      for (pitch <- 1 until maxPitch; note <- notes ) {
	map = map + ((note+pitch) -> index);
	index = index+1;
      }
      map + (("C"+maxPitch) -> index)
      // return will inject this map in midiCodes
    }
  override val notes : Seq[String] = (midiCodes keys).toSeq
  /**
  * Plays the given Chord (or Note) with the midi synthesizer set with the
  * given instrument midi channel.
  * Pre-conditions: midi sequencer should be available, midiCodes must have
  * been initialised
  * Post-condition: played sounds have been stopped, (threads have been stopped)
  */
  // TODO: catch exceptions (key not found + channels + noteOn)
  override def play (chord : Chord [_], instru : Int) = {
    playServer ! SetInstrument(instru)
    playServer ! PlayChord(chord)
    playServer ! SetInstrument(_preferences.instrument)
  }

  override def play (chord: Chord[_]) = {
    playServer ! SetInstrument(_preferences.instrument)
    playServer ! PlayChord(chord)
  }

  playServer ! Init

}

object MidiPlayer 
{

  def checkDevices = { 
    val devices : Array[MidiDevice.Info] = MidiSystem.getMidiDeviceInfo();
    if (devices.length == 0) {
      println("No MIDI devices found");
    } else {
      for (dev : MidiDevice.Info <- devices) {
	println(dev);
      }
    }
  }
}


// Midi preferences

class MidiPreferences extends Preferences {
  // TODO: load preferences from file
  var _volume : Int = 100
  def volume = _volume
  def volume_=(v: Int) = {
    if (v < 0) {
      _volume = 0
    } else if (v > 100) {
      _volume = 100
    } else {
      _volume = v
    }
  }

  // delay between two notes in a chord
  var _delay: Int = 100 //in ms
  def delay_=(d: Int) = if (d < 0) { _delay = 0 } else { _delay = d }
  def delay = _delay
  
  // total duration of the total chord (all notes played)
  var _duration: Int = 500 // in ms
  def duration_=(d: Int) = if (d < 0) { _duration = 0 } else { _duration = d }
  def duration = _duration
  
  // instrument with which sounds are played
  var _instrument: Int = 0
  def instrument_=(c: Int) = {
    if (c < 0) { _instrument = 0 }
    else if (c > 255) { _instrument = 255 }
    else _instrument = c
  }
  def instrument = _instrument

  // config dialog 
  var _dialog = new sw.Dialog {
    class LocalSlider(_name : String,
		      _min : Int, _max : Int, 
		      _value : Int) 
    extends sw.Slider {
      min = _min
      max = _max
      value = _value
      name = _name
      majorTickSpacing = (max - min) / 2
      minorTickSpacing = majorTickSpacing / 2
      var middle = min+majorTickSpacing;
      labels = Map(min -> new sw.Label(""+min), 
		   max -> new sw.Label(""+max),
		   middle -> new sw.Label(""+middle))
      paintLabels = true
      paintTicks = true
    }
    title = "Midi Player preferences"
    
    contents = new sw.GridPanel(5, 1) {
      val nv = new LocalSlider("nv", 0, 100, 100)
      val nd = new LocalSlider("nd", 0, 1000, 100)
      val t = new LocalSlider("t", 100, 2000, 500)
      val inst = new sw.ComboBox(for (i <- 0 to 255) yield i) {
	name = "inst"
      }
      contents += new sw.FlowPanel {
	contents += new sw.Label("Note volume")
	contents += nv

      }
      contents += new sw.FlowPanel {
	contents += new sw.Label("Note delay (ms)")
	contents += nd
      }
      contents += new sw.FlowPanel { 
	contents += new sw.Label("Duration (ms)")
	contents += t
      }
      contents += new sw.FlowPanel { 
	contents += new sw.Label("Instrument")
	contents += inst
      }
      contents += new sw.Button{
	action = new sw.Action("OK"){
	  def apply = {
	    volume = nv.value
	    delay = nd.value
	    duration = t.value
	    instrument = inst.peer.getSelectedItem.asInstanceOf[Int]
	    close
	  }
	}
      }
    }
    pack()
  }
  override def dialog : sw.Dialog = _dialog
}
