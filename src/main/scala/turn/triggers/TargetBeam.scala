package turn.triggers

import board.Coordinate
import piece.Piece
import turn.effects.{Place, Remove, RemoveTrigger}
import turn.{Effect, InitiatingAction, Trigger}

case class TargetBeam(targetedCoordinate: Coordinate, attackerCoordinate: Coordinate, attacker: Piece, direction: Coordinate => Coordinate) extends Trigger {
  override def watchedCoordinates: Iterable[Coordinate] = Seq(targetedCoordinate, attackerCoordinate)
  override def reaction(initiatingAction: InitiatingAction): List[Effect] = {
    /*
    Piece's all leave targeted spot => Extend this beam
    A Piece enters targeted spot => Shorten this beam
    Attacker leaves spot => remove this trigger
     */
    initiatingAction match {
      case Place(executor, at) if at == targetedCoordinate => {
        ???
      }
      case Remove(executor) if targetedSpace.occupiers.contains(executor) && targetedSpace.occupiers.isEmpty => {
        ???
      }
      case Remove(executor) if executor == attacker => {
        List(RemoveTrigger(this))
      }
    }
  }
}
