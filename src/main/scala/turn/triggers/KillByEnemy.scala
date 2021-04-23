package turn.triggers

import board.Coordinate
import piece.Piece
import turn.effects.{Kill, RemoveTrigger}
import turn.initiatingactions.Move
import turn.{Effect, InitiatingAction, Trigger}

case class KillByEnemy(killCoordinate: Coordinate, target: Piece) extends Trigger {
  override def watchedCoordinates: Iterable[Coordinate] = Seq(killCoordinate)

  override def reaction(initiatingAction: InitiatingAction): List[Effect] = {
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
