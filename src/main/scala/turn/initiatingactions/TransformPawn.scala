package turn.initiatingactions

import board.{BoardState, Coordinate}
import piece.{Pawn, Piece}
import team.Team
import turn.effects.{Place, Remove}
import turn.{Effect, InitiatingAction}

case class TransformPawn(executor: Pawn, into: Piece) extends InitiatingAction {
  override def initiate(boardState: BoardState): List[Effect] = {
    val newCoordinate: Coordinate = boardState.pieces(executor).alongColumn(Team.getDirection(executor.team))
    val moveEffects: List[Effect] = Move(executor, newCoordinate).initiate(boardState)
    moveEffects :++ List(Remove(executor), Place(into, newCoordinate))
  }
}
