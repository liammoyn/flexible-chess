package turn.sideeffects

import board.Space
import piece.Piece
import turn.SideEffect

case class Kill(pieces: Iterable[Piece]) extends SideEffect {
  val pieceSet: Set[Piece] = Set(this.pieces)

  override def execute(space: Space): Space = {
    val newOccupiers = space.occupiers.filter(p => pieceSet.contains(p))
    space.createCopy(newOccupiers, space.activeTriggers)
  }
}
