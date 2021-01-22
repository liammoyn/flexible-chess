package turn.initiatingactions

import board.{BoardState, Coordinate}
import piece.Pawn
import team.Team
import turn.InitiatingAction
import turn.triggers.EnPassantKill

case class PawnTwoStepMove(executor: Pawn) extends InitiatingAction {
  override def execute(boardState: BoardState): BoardState = {
    val toCoordinate: Coordinate = boardState.pieces(executor).alongColumn(2 * Team.getDirection(executor.team))
    val movedBoardState: BoardState = Move(executor, toCoordinate).execute(boardState)

    val newPawnSpace = movedBoardState
      .getSpace(executor)
      .map(space => space.addTrigger(
        EnPassantKill(space.coordinate.alongColumn(Team.getDirection(executor.team) * -1), executor)
      ))
      .get

    movedBoardState.updateSpace(newPawnSpace)
  }
}
