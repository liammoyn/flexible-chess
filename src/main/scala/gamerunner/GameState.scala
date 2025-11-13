package gamerunner

import board.BoardState
import team.Team.Team
import turn.effects.{AddPiece, AddTrigger, AdvanceTurn, RemovePiece, RemoveTrigger}
import turn.{Effect, Trigger}

class GameState(val boardState: BoardState,
                val triggers: Set[Trigger],
                val players: Map[Team, Player],
                val turn: Team) {

  /**
   * Apply the given effect to the GameState, don't check triggers.
   */
  def applyEffect(effect: Effect): GameState = {
    val newBoardState = effect match {
      case AddPiece(executor, at) => this.boardState.addPiece(executor, at)
      case RemovePiece(executor) => this.boardState.removePiece(executor)
      case _ => boardState
    }

    val newTriggers = effect match {
      case AddTrigger(trigger) => triggers + trigger
      case RemoveTrigger(trigger) => triggers - trigger
      case _ => triggers
    }

    val newTurn = effect match {
      case AdvanceTurn(nextTurn) => nextTurn
      case _ => turn
    }

    new GameState(newBoardState, newTriggers, players, newTurn)
  }

  def getResultingEffects(effect: Effect): Seq[Effect] = {
    triggers.foldLeft(Seq[Effect]())((acc, trigger) => {
      acc ++ trigger.reaction(effect)
    })
  }
}
