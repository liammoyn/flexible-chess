package turn.actions

import board.{BoardState, Coordinate, Space}
import piece.Piece
import turn.InitiatingAction

case class Move(executer: Piece, to: Coordinate) extends InitiatingAction {
  override def execute(boardState: BoardState): Option[BoardState] = {
    val fromSpace: Option[Space] = boardState.getSpace(executer)
    val newFromSpace: Option[Space] = fromSpace.map(space => space.removePiece(executer))
    val newToSpace: Option[Space] = boardState.getSpace(to).map(space => space.addPiece(executer))

    newFromSpace.flatMap(fromS => newToSpace.map(toS => boardState.updateSpaces(Seq(
      fromS,
      toS
    ))))
  }

  override val executionOrder: Int = 2
}
