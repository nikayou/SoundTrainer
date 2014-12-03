package soundTrainer;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;

import akka.actor._

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
class MidiPlayer extends Player {

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
    chord.notes foreach { 
      x => channels (instru) noteOn (midiCodes(x.toString), 100);
      //      Thread.sleep(100) //TODO: customise this delay
      }
    }

  }

  object MidiPlayer {

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
