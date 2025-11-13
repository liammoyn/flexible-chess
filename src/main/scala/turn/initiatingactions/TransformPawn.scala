package turn.initiatingactions

import board.{BoardState, Coordinate}
import gamerunner.GameState
import piece.{Pawn, Piece}
import team.Team
import turn.effects.{AddPiece, RemovePiece}
import turn.{Effect, InitiatingAction}

case class TransformPawn(executor: Pawn, into: Piece) extends InitiatingAction {
  override def initiate(gameState: GameState): List[Effect] = {
    val newCoordinate: Coordinate = gameState.boardState.pieces(executor).alongColumn(Team.getDirection(executor.team))
    val moveEffects: List[Effect] = Move(executor, newCoordinate).initiate(gameState)
    moveEffects :++ List(RemovePiece(executor), AddPiece(into, newCoordinate))
  }
}
