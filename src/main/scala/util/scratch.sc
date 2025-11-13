case class ab(in: Iterable[Int])

val triggers: Iterable[ab] = Seq(
  ab(Seq(1, 2, 3)),
  ab(Seq(2, 3, 4)),
  ab(Seq(1, 4, 5, 3))
)

val coordinateTriggers: Map[Int, Iterable[ab]] = triggers
  .flatMap(t => t.in.map(i => (i, t)))
  .groupMap(_._1)(_._2)
