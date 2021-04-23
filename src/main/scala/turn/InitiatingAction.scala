package turn

import board.BoardState
import piece.Piece

trait InitiatingAction {
  def executor: Piece

  def initiate(boardState: BoardState): List[Effect]
}
