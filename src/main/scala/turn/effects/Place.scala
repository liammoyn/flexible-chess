package turn.effects

import board.{BoardState, Coordinate, Space}
import piece.Piece
import turn.triggers.KillByEnemy
import turn.{Effect, InitiatingAction}

case class Place(executor: Piece, at: Coordinate) extends Effect with InitiatingAction {
  // TODO: Add target triggers
  override def execute(boardState: BoardState): BoardState = {
    val placedSpace: Space = boardState.getSpace(at).map(space => {
      space
        .addPiece(executor)
    }).get
    boardState
      .updateSpace(placedSpace, this)
      .addTrigger(KillByEnemy(at, executor))
  }

  override def initiate(boardState: BoardState): List[Effect] = {
    List(this)
  }
}
