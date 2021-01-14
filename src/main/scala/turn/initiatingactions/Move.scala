package turn.initiatingactions

import board.{BoardState, Coordinate, Space}
import piece.Piece
import turn.InitiatingAction

case class Move(executor: Piece, to: Coordinate) extends InitiatingAction {
  override def execute(boardState: BoardState): BoardState = {
    val newFromSpace: Space = boardState.getSpace(executor).map(space => space.removePiece(executor)).get

    executor.hasMoved = true
    val newToSpace: Space = boardState.getSpace(to).map(space => space.addPiece(executor)).get

    boardState.updateSpaces(Seq(newFromSpace, newToSpace))
  }
}
