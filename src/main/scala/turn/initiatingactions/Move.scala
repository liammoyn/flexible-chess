package turn.initiatingactions

import board.{BoardState, Coordinate}
import piece.Piece
import turn.InitiatingAction

case class Move(executor: Piece, to: Coordinate) extends InitiatingAction {
  override def execute(boardState: BoardState): BoardState = {
    executor.hasMoved = true
    val actions = Seq(Remove(executor), Place(executor, to))
    actions.foldLeft(boardState)((bs, ia) => ia.execute(bs))
  }
}
