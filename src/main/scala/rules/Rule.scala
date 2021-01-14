package rules

import board.BoardState
import team.Team.Team

trait Rule {
  // Any rule that concerns the entire board rather than a specific action/effect
  def validTurn(beginningBoardState: BoardState, endingBoardState: BoardState, turn: Team): Boolean
}
