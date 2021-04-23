package turn.initiatingactions

import board.{BoardState, Coordinate}
import piece.{King, Piece}
import turn.{Effect, InitiatingAction}

case class Castle(executor: King, rook: Piece) extends InitiatingAction {
  // TODO: Need to check validity somewhere
  override def initiate(boardState: BoardState): List[Effect] = {
    val oldKingCoordinate: Coordinate = boardState.pieces(executor)
    val oldRookCoordinate: Coordinate = boardState.pieces(rook)

    val rowDirection: Int = Math.max(Math.min(oldRookCoordinate.col - oldKingCoordinate.col, 1), -1)

    val newKingCoordinate: Coordinate = oldKingCoordinate.alongRow(rowDirection * 2)
    val newRookCoordinate: Coordinate = newKingCoordinate.alongRow(rowDirection * -1)

    Move(executor, newKingCoordinate).initiate(boardState) :++ Move(rook, newRookCoordinate).initiate(boardState)
  }
}
