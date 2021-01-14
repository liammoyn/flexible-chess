package turn.initiatingactions

import board.{BoardState, Space}
import piece.{King, Piece}
import turn.InitiatingAction

case class Castle(executor: King, rook: Piece) extends InitiatingAction {
  // TODO: Need to check validity somewhere
  override def execute(boardState: BoardState): BoardState = {
    val oldKingSpace: Space = boardState.getSpace(executor).map(space => space.removePiece(executor)).get
    val oldRookSpace: Space = boardState.getSpace(rook).map(space => space.removePiece(rook)).get

    val rowDirection: Int = Math.max(Math.min(oldKingSpace.coordinate.col - oldRookSpace.coordinate.col, 1), -1)

    executor.hasMoved = true
    rook.hasMoved = true
    val newKingSpace: Space = boardState.getSpace(oldKingSpace.coordinate.alongRow(rowDirection * 2))
        .map(space => space.addPiece(executor))
        .get
    val newRookSpace: Space = boardState.getSpace(newKingSpace.coordinate.alongRow(-1 * rowDirection))
      .map(space => space.addPiece(rook))
      .get

    boardState.updateSpaces(Seq(oldKingSpace, oldRookSpace, newKingSpace, newRookSpace))
  }
}
