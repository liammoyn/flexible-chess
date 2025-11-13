package turn.triggers

import board.Coordinate
import piece.Piece
import turn.effects.{RemovePiece, RemoveTrigger}
import turn.initiatingactions.Move
import turn.{Effect, InitiatingAction, Trigger}

/**
 * Used when a piece is targeting a specific tile.
 *
 * @param targetedCoordinate The coordinate that is being targeted by the attacker.
 * @param attackerCoordinate The coordinate that the attacker is currently at.
 * @param attacker The piece that is doing the targeting.
 */
case class Target(targetedCoordinate: Coordinate, attackerCoordinate: Coordinate, attacker: Piece) extends Trigger {
  override def reaction(initiatingEffect: Effect): List[Effect] = {
    initiatingEffect match {
      case RemovePiece(executor) if executor == attacker => {
        List(RemoveTrigger(this))
      }
    }
  }
}
