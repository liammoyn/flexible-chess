package gamerunner

import board.BoardState
import team.Team.Team
import turn.InitiatingAction

trait Player {
  def takeTurn(gameState: GameState, team: Team): InitiatingAction
}
