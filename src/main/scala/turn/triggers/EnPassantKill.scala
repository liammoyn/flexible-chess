package turn.triggers

import board.Coordinate
import piece.Pawn
import turn.initiatingactions.Move
import turn.sideeffects.{Kill, RemoveTrigger}
import turn.{InitiatingAction, SideEffect, Trigger}

case class EnPassantKill(killCoordinate: Coordinate, target: Pawn) extends Trigger {
  override def reaction(initiatingAction: InitiatingAction): List[SideEffect] = {
    initiatingAction match {
      case Move(executor, coordinate) if (coordinate == killCoordinate && executor.team != target.team) => {
        List(Kill(Set(target)))
      }
      case _ if initiatingAction.executor.team == target.team => {
        List(RemoveTrigger(this))
      }
      case _ => List()
    }
  }
}
