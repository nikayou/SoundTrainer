package soundTrainer;

import scala.xml.XML

class XMLChordLoader {
  type C = Chord[String]
  var chords : Array[C] = Array()
  def createChordFromXMLNode (node: xml.Node) : C = {
    val notes = (node text) split " "
    var noteList : List[String] = List()
    for (n <- notes)
      noteList = noteList :+ n
    val name : String = ((node \\ "@id") text)
    new Chord (name, noteList)
  }
  def loadFromXML (path: String) {
    val xml = XML.loadFile(path)
    val chords = (xml \\ "CHORD")
    var chordsList : List[C] = List()
    chords.foreach (n => {chordsList = chordsList :+ createChordFromXMLNode(n) })
    chordsList foreach (println(_))
  }
}
