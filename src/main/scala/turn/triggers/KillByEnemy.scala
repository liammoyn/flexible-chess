package turn.triggers

import board.Coordinate
import piece.Piece
import turn.initiatingactions.Move
import turn.sideeffects.{Kill, RemoveTrigger}
import turn.{InitiatingAction, SideEffect, Trigger}

case class KillByEnemy(killCoordinate: Coordinate, target: Piece) extends Trigger {
  override def reaction(initiatingAction: InitiatingAction): List[SideEffect] = {
    initiatingAction match {
      case Move(executor, coordinate) if coordinate == killCoordinate && executor.team != target.team => {
        List(Kill(Set(target)))
      }
      case Move(executor, coordinate) if executor == target && coordinate != killCoordinate => {
        List(RemoveTrigger(this))
      }
      case _ => List()
    }
  }
}
