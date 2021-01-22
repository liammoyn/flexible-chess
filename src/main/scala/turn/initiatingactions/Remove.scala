package turn.initiatingactions

import board.{BoardState, Space}
import piece.Piece
import turn.InitiatingAction
import turn.triggers.KillByEnemy

case class Remove(executor: Piece) extends InitiatingAction {
  // TODO: Remove target triggers
  override def execute(boardState: BoardState): BoardState = {
    val removedSpace: Space = boardState.getSpace(executor).map(space => {
      space
        .removePiece(executor)
        .removeTrigger(KillByEnemy(boardState.pieces(executor), executor))
    }).get
    boardState.updateSpace(removedSpace)
  }
}
