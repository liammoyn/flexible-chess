package turn.triggers

import board.Coordinate
import piece.Pawn
import turn.effects.{Kill, RemoveTrigger}
import turn.initiatingactions.Move
import turn.{Effect, InitiatingAction, Trigger}

case class EnPassantKill(killCoordinate: Coordinate, target: Pawn) extends Trigger {
  override def watchedCoordinates: Iterable[Coordinate] = Seq(killCoordinate)

  override def reaction(initiatingAction: InitiatingAction): List[Effect] = {
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
