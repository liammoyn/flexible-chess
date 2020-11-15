package turn.actions

import board.{BoardState, Space}
import piece.Piece
import turn.SideAction

case class Kill(enemy: Piece) extends SideAction {
  override def execute(boardState: BoardState): Option[BoardState] = {
    val enemySpace: Option[Space] = boardState.getSpace(enemy)
    val newSpace: Option[Space] = enemySpace.map(space => space.removePiece(enemy))
    newSpace.map(space => boardState.updateSpace(space))
  }

  override val executionOrder: Int = 1
}
