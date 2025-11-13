package turn.initiatingactions

import board.{BoardState, Coordinate}
import gamerunner.GameState
import piece.Piece
import turn.effects.{AddPiece, RemovePiece}
import turn.{Effect, InitiatingAction}

case class Move(executor: Piece, to: Coordinate) extends InitiatingAction {
  override def initiate(gameState: GameState): List[Effect] = {
    executor.hasMoved = true // TODO: Put this in an effect
    List(RemovePiece(executor), AddPiece(executor, to))
  }
}
