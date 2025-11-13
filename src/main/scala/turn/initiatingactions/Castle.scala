package turn.initiatingactions

import board.{BoardState, Coordinate}
import gamerunner.GameState
import piece.{King, Piece}
import turn.{Effect, InitiatingAction}

case class Castle(executor: King, rook: Piece) extends InitiatingAction {
  // TODO: Need to check validity somewhere
  override def initiate(gameState: GameState): List[Effect] = {
    val oldKingCoordinate: Coordinate = gameState.boardState.pieces(executor)
    val oldRookCoordinate: Coordinate = gameState.boardState.pieces(rook)

    val rowDirection: Int = Math.max(Math.min(oldRookCoordinate.col - oldKingCoordinate.col, 1), -1)

    val newKingCoordinate: Coordinate = oldKingCoordinate.alongRow(rowDirection * 2)
    val newRookCoordinate: Coordinate = newKingCoordinate.alongRow(rowDirection * -1)

    Move(executor, newKingCoordinate).initiate(gameState) :++ Move(rook, newRookCoordinate).initiate(gameState)
  }
}
