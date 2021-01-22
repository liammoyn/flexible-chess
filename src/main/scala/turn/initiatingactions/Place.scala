package turn.initiatingactions

import board.{BoardState, Coordinate, Space}
import piece.Piece
import turn.InitiatingAction
import turn.triggers.KillByEnemy

case class Place(executor: Piece, at: Coordinate) extends InitiatingAction{
  // TODO: Add target triggers
  override def execute(boardState: BoardState): BoardState = {
    val placedSpace: Space = boardState.getSpace(at).map(space =>{
      space
        .addPiece(executor)
        .addTrigger(KillByEnemy(at, executor))
    }).get
    boardState.updateSpace(placedSpace)
  }
}
