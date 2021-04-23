package turn.triggers

import board.Coordinate
import piece.Piece
import turn.effects.RemoveTrigger
import turn.initiatingactions.Move
import turn.{Effect, InitiatingAction, Trigger}

case class Target(targetedCoordinate: Coordinate, attackerCoordinate: Coordinate, attacker: Piece) extends Trigger {
  override def watchedCoordinates: Iterable[Coordinate] = Seq(targetedCoordinate, attackerCoordinate)

  override def reaction(initiatingAction: InitiatingAction): List[Effect] = {
    initiatingAction match {
      case Move(executor, to) if executor == attacker && to != attackerCoordinate => {
        List(RemoveTrigger(this))
      }
    }
  }
}
