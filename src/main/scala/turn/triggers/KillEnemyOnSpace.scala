package turn.triggers

import board.BoardState
import turn.actions.{Kill, Move}
import turn.{InitiatingAction, SideAction, Trigger}

class KillEnemyOnSpace extends Trigger{
  override def reaction(initiatingAction: InitiatingAction, currentBoardState: BoardState): List[SideAction] = {
    initiatingAction match {
      case move: Move => {
        currentBoardState.getSpace(move.to)
          .get
          .occupiers
          .filterNot(p => p.team.equals(move.executer.team))
          .map(p => Kill(p))
      }
      case _ => List()
    }
  }
}
