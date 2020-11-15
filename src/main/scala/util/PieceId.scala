package util

case class PieceId private (id: Int) extends AnyVal {
}

object PieceId {
  var idCounter = 0

  def apply(): PieceId = {
    idCounter += 1
    PieceId(idCounter)
  }
}
