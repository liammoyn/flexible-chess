package turn

import board.BoardState
import team.Team.Team

trait Action {
  val executionOrder: Int
  def execute(boardState: BoardState): Option[BoardState]
}
