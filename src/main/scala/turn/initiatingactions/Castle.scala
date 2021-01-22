package turn.initiatingactions

import board.{BoardState, Coordinate, Space}
import piece.{King, Piece}
import turn.InitiatingAction

case class Castle(executor: King, rook: Piece) extends InitiatingAction {
  // TODO: Need to check validity somewhere
  override def execute(boardState: BoardState): BoardState = {
    val oldKingCoordinate: Coordinate = boardState.pieces(executor)
    val oldRookCoordinate: Coordinate = boardState.pieces(rook)

    val rowDirection: Int = Math.max(Math.min(oldRookCoordinate.col - oldKingCoordinate.col, 1), -1)

    val newKingCoordinate: Coordinate = oldKingCoordinate.alongRow(rowDirection * 2)
    val newRookCoordinate: Coordinate = newKingCoordinate.alongRow(rowDirection * -1)

    val actions = Seq(Move(executor, newKingCoordinate), Move(rook, newRookCoordinate))
    actions.foldLeft(boardState)((bs, move) => move.execute(bs))
  }
}
