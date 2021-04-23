package turn.initiatingactions

import board.{BoardState, Coordinate}
import piece.Pawn
import team.Team
import turn.effects.AddTrigger
import turn.triggers.EnPassantKill
import turn.{Effect, InitiatingAction}

case class PawnTwoStepMove(executor: Pawn) extends InitiatingAction {
  override def initiate(boardState: BoardState): List[Effect] = {
    val toCoordinate: Coordinate = boardState.pieces(executor).alongColumn(2 * Team.getDirection(executor.team))

    val moveEffects: List[Effect] = Move(executor, toCoordinate).initiate(boardState)

    moveEffects :+ AddTrigger(EnPassantKill(toCoordinate.alongColumn(Team.getDirection(executor.team) * -1), executor))
  }
}
