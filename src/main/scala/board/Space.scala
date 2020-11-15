package board

import piece.Piece
import turn.{Action, ActionResponder}
import turn.actions.{Kill, Move, Transform}

class Space private (val coordinate: Coordinate, val occupiers: List[Piece]) {
  def removePiece(piece: Piece): Space = {
    this.updateSpace(occupiers.filterNot(p => p.equals(piece)))
  }

  def addPiece(piece: Piece): Space = {
    this.updateSpace(piece :: occupiers)
  }

  private def updateSpace(occupiers: List[Piece]): Space = new Space(this.coordinate, occupiers)
}

object Space {
  def apply(at: Coordinate): Space = new Space(at, List())
  def apply(at: Coordinate, piece: Piece): Space = new Space(at, List(piece))
}
