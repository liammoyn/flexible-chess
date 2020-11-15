package turn.actions

import board.{BoardState, Space}
import piece.Piece
import turn.SideAction

case class Transform(executer: Piece, into: Piece) extends SideAction {

  override def execute(boardState: BoardState): Option[BoardState] = {
    val currentSpace: Option[Space] = boardState.getSpace(executer)
    // TODO: Do we want transform to take a Piece or a type of Piece?
    val updatedSpace = currentSpace.map(space => space.removePiece(executer).addPiece(into))

    updatedSpace.map(space => boardState.updateSpace(space))
  }

  override val executionOrder: Int = 3
}
