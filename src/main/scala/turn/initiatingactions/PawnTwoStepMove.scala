package turn.initiatingactions

import board.{BoardState, Space}
import piece.Pawn
import team.Team
import turn.InitiatingAction
import turn.triggers.EnPassantKill

case class PawnTwoStepMove(executor: Pawn) extends InitiatingAction {
  override def execute(boardState: BoardState): BoardState = {
    val newFromSpace: Space = boardState.getSpace(executor).map(space => space.removePiece(executor)).get

    executor.hasMoved = true
    val newToSpace:Space = boardState.getSpace(newFromSpace.coordinate.alongColumn(2 * Team.getDirection(executor.team)))
      .map(space => space
        .addPiece(executor)
        .addTrigger(new EnPassantKill(newFromSpace.coordinate.alongColumn(Team.getDirection(executor.team)), executor))
      )
      .get

      boardState.updateSpaces(Seq(newFromSpace, newToSpace))
  }
}
