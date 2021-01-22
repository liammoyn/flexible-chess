package turn.initiatingactions

import board.{BoardState, Coordinate, Space}
import piece.{Pawn, Piece}
import team.Team
import turn.InitiatingAction

case class TransformPawn(executor: Pawn, into: Piece) extends InitiatingAction {
  override def execute(boardState: BoardState): BoardState = {
    val newCoordinate: Coordinate = boardState.pieces(executor).alongColumn(Team.getDirection(executor.team))
    val actions = Seq(Move(executor, newCoordinate), Remove(executor), Place(into, newCoordinate))
    actions.foldLeft(boardState)((bs, ia) => ia.execute(bs))
  }
}
