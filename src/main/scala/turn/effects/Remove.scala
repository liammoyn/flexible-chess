package turn.effects

import board.{BoardState, Space}
import piece.Piece
import turn.Effect
import turn.triggers.KillByEnemy

case class Remove(executor: Piece) extends Effect {
  // TODO: Remove "target" triggers when implemented
  override def execute(boardState: BoardState): BoardState = {
    val removedSpace: Space = boardState.getSpace(executor).map(space => {
      space
        .removePiece(executor)
    }).get
    boardState
      .updateSpace(removedSpace, this)
      .removeTrigger(KillByEnemy(boardState.pieces(executor), executor))
  }
}
