package turn.initiatingactions

import board.{BoardState, Coordinate}
import gamerunner.GameState
import piece.Pawn
import team.Team
import turn.effects.AddTrigger
import turn.triggers.EnPassantKill
import turn.{Effect, InitiatingAction}

case class PawnTwoStepMove(executor: Pawn) extends InitiatingAction {
  override def initiate(gameState: GameState): List[Effect] = {
    val toCoordinate: Coordinate = gameState.boardState.pieces(executor).alongColumn(2 * Team.getDirection(executor.team))

    val moveEffects: List[Effect] = Move(executor, toCoordinate).initiate(gameState)

    moveEffects :+ AddTrigger(EnPassantKill(toCoordinate.alongColumn(Team.getDirection(executor.team) * -1), executor))
  }
}
