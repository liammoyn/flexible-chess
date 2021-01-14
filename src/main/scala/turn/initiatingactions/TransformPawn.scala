package turn.initiatingactions

import board.{BoardState, Space}
import piece.{Pawn, Piece}
import team.Team
import turn.InitiatingAction

case class TransformPawn(executor: Pawn, into: Piece) extends InitiatingAction {
  override def execute(boardState: BoardState): BoardState = {
    val newFromSpace: Space = boardState.getSpace(executor).map(space => space.removePiece(executor)).get

    into.hasMoved = true
    val teamDirection = Team.getDirection(executor.team)
    val newToSpace: Space = boardState
      .getSpace(newFromSpace.coordinate.alongColumn(teamDirection))
      .map(space => space.addPiece(into))
      .get

    boardState.updateSpaces(Seq(newFromSpace, newToSpace))
  }
}
