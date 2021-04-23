package turn.initiatingactions

import board.{BoardState, Coordinate}
import piece.Piece
import turn.effects.{Place, Remove}
import turn.{Effect, InitiatingAction}

case class Move(executor: Piece, to: Coordinate) extends InitiatingAction {
  override def initiate(boardState: BoardState): List[Effect] = {
    executor.hasMoved = true // TODO: Put this in an effect
    List(Remove(executor), Place(executor, to))
  }
}
