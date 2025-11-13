package gamerunner

import board.{BoardState, Space}
import main.Viewer
import piece.{King, Piece}
import team.Team
import team.Team.Team
import turn.{Effect, InitiatingAction}

import scala.collection.mutable

class Referee(private var gameState: GameState) {
  def playGame(players: List[Player]): Player = {
    val teams: Map[Team, Player] = players
      .zipWithIndex
      .map(pi => (Team.values.toList(pi._2 % Team.values.size), pi._1))
      .toMap
    val turnList: List[Team] = Team.values.toList
    var turnIndex: Int = 0
    while (getWinner.isEmpty) {
      println(Viewer.getBoardString(gameState.boardState))
      val playerTurn = teams(turnList(turnIndex))
      this.gameState = playTurn(this.gameState, playerTurn, turnList(turnIndex))
      turnIndex = (turnIndex + 1) % turnList.size
    }
    teams(getWinner.get)
  }

  private def playTurn(gameState: GameState, player: Player, playingTeam: Team): GameState = {
    val playerTurn: InitiatingAction = player.takeTurn(gameState, playingTeam)

    // TODO: Check that initiating action is valid

    var nextGameState: GameState = gameState
    val effectsToApply: mutable.Stack[Effect] = mutable.Stack(playerTurn.initiate(gameState))
    while (effectsToApply.nonEmpty) {
      // Get the next effect that needs to be applied
      val effectToApply = effectsToApply.pop()
      // Check the triggers to see if this effect creates more effects
      val resultingEffects: Seq[Effect] = gameState.getResultingEffects(effectToApply)
      // Modify the game state based on the effect
      nextGameState = gameState.applyEffect(effectToApply)
      // Add all the new effects onto the list of effects to apply
      effectsToApply.pushAll(resultingEffects)
    }

    // TODO: Check that resulting game state is valid

    nextGameState
  }

  // TODO: This needs to be made more general / flexible
  private def getWinner: Option[Team] = {
    val remainingKings: List[Piece] = gameState.boardState.getAllSpaces
      .foldLeft(List[Piece]())((acc, cur) => acc.:::(cur
        .occupiers
        .filter((p: Piece) => {
          p match {
            case p: King => true
            case p => false
          }
        })
      ))
    if (remainingKings.size == 1) {
      Some(remainingKings.head.team)
    } else {
      None
    }
  }
}

object Referee {
  def validMoves(boardState: BoardState, turn: Team): List[InitiatingAction] = {
    boardState.pieces
      .filter(_._1.team == turn)
      .flatMap(pieceCoordinate => pieceCoordinate._1.validMoves(pieceCoordinate._2, boardState))
      .toList
  }
}
