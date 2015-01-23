package soundTrainer

trait Observer[Evt] {

  def receive (e: Evt) : Unit
  def observe (s: Observable[Evt]) : Unit = s.addObserver(this)

}

trait Observable[Evt] {

  private var observers : List[Observer[Evt]] = Nil
  def addObserver (o: Observer[Evt]) = { observers = observers:+o }
  def publishTo (e: Evt, o: Observer[Evt]) = o.receive(e)
  def publish (e: Evt) = observers map { x => x.receive(e) }
  
}
