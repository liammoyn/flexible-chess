package gamerunner

import board.BoardState
import team.Team.Team
import turn.InitiatingAction

import scala.util.Random

class ComputerPlayer extends Player {

  private val rand = new Random()

  override def takeTurn(gameState: GameState, team: Team): InitiatingAction = {
    val allPossibleMoves = Referee.validMoves(gameState.boardState, team)
    allPossibleMoves(rand.between(0, allPossibleMoves.size))
  }

}
