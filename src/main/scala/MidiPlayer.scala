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
case class PlayNote(note: Int)
case object Stop

/*
 * MidiPlayer is one concrete class for Player that plays notes using a
 * midi sequencer. It holds a map [String->Int] whose keys are the name of the
 * note (in international notation) and the values are the midi code.
 * // TODO: 
 * - close sequencer when program ends
 * - check if notes and codes can varry with a different synthesizer
 * - check if a separate thread for playing sounds is a good idea
 * - handle exception with devices, threads and availability
 */
class MidiPlayer extends Player 
{

  val _preferences = new MidiPreferences
  override val preferences = Some(_preferences)

  class PlayServer extends Actor 
  {

    def receive = {
      case SetInstrument(i) => channels (_preferences.channel) programChange i
      case PlayNote(n) 
      => channels (_preferences.channel) noteOn (n, _preferences.volume);
      case Stop => channels (_preferences.channel) allNotesOff
    }
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
  // TODO: maybe play that in a separate thread in order not to block?
  // TODO: catch exceptions (key not found + channels + noteOn)
  override def play (chord : Chord [_], instru : Int) = {
    playServer ! SetInstrument(instru)
    chord.notes foreach { 
      x => playServer ! PlayNote(midiCodes(x.toString))
      Thread.sleep(_preferences.delay) //TODO: customise this delay
    }
    
    Thread.sleep(_preferences.duration) // TODO: customise duration of chord
    playServer ! Stop
  }

  def play (note : String, instru: Int) = {
    playServer ! SetInstrument(instru)
    playServer ! PlayNote(midiCodes(note.toString))
    Thread.sleep(_preferences.duration) // TODO: customise duration of chord
    playServer ! Stop    
  }

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
  
  // channel with which sounds are played
  var _channel: Int = 0
  def channel_=(c: Int) = {
    if (c < 0) { _channel = 0 }
    else if (c > 255) { _channel = 255 }
    else _channel = c
  }
  def channel = _channel

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
    contents = new sw.GridPanel(4, 1) {
      
      contents += new sw.FlowPanel {
	contents += new sw.Label("Note volume")
	contents += new LocalSlider("vs", 0, 100, 100)

      }
      contents += new sw.FlowPanel {
	contents += new sw.Label("Note delay (ms)")
	contents += new LocalSlider("nd", 0, 1000, 100)
      }
      contents += new sw.FlowPanel { 
	contents += new sw.Label("Duration (ms)")
	contents += new LocalSlider("t", 100, 2000, 500)
      }
      contents += new sw.Label("Channel")
    }
    pack()
  }
  override def dialog : sw.Dialog = _dialog
}
