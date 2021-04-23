package turn

import board.BoardState

trait Effect {
  def execute(boardState: BoardState): BoardState
}
