package gamerunner

import board.BoardState
import team.Team.Team
import turn.InitiatingAction

trait Player {
  def takeTurn(boardState: BoardState, team: Team): InitiatingAction
}
