package soundTrainer

import scala.xml.XML


object XMLChordLoader 
{
  type C = Chord[String]
  def createChordFromXMLNode (node: xml.Node) : C = {
    // a Chord[String] is a name and a list of string
    val notes = (node text) split " "
    var noteList : List[String] = List()
    for (n <- notes)
      noteList = noteList :+ n
    val name : String = ((node \\ "@id") text)
    new Chord (name, noteList)
  }
  def loadFromXML (path: String) : List[C] = {
    val xml = XML.loadFile(path)
    val chords = (xml \\ "CHORD")
    var chordsList : List[C] = List()
    // foreach node "CHORD", create a Chord with its data and add it to list
    chords.foreach (n => {chordsList = chordsList :+ createChordFromXMLNode(n) })
    chordsList
  }
}
