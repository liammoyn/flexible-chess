package turn.triggers

import board.{Coordinate, Space}
import piece.Piece
import turn.effects.{AddPiece, AddTrigger, RemovePiece, RemoveTrigger}
import turn.{Effect, InitiatingAction, Trigger}

import scala.+:

/**
 * Used when a piece is targeting in a straight line. This trigger refers to only a single tile that is being
 * targeted, but it is assumed to be in a chain of 1 or more related TargetBeam triggers in a line.
 *
 * @param targetedCoordinate The tile that is being targeted by this trigger.
 * @param attackerCoordinate The tile that the attacker is currently on.
 * @param attacker The piece that is targeting with this trigger.
 * @param getNextSpace The next space in the beam, should be away from attacker. TODO: Should be a stream probably
 */
case class TargetBeam(targetedSpace: Space,
                      attackerSpace: Space,
                      attacker: Piece,
                      getNextSpace: Space => Option[Space]) extends Trigger {
  override def reaction(initiatingEffect: Effect): List[Effect] = {
    /*
    A Piece enters targeted spot => Shorten this beam
    Pieces all leave targeted spot => Extend this beam
    Attacker leaves spot => remove this trigger
     */
    initiatingEffect match {
      case AddPiece(executor, at) if at == targetedSpace.coordinate => {
        mapNextSpaceToEffect(space => RemoveTrigger(???))
      }
      case RemovePiece(executor) if targetedSpace.occupiers.contains(executor) && targetedSpace.occupiers.size == 1 => {
        mapNextSpaceToEffect(space => AddTrigger(TargetBeam(space, attackerSpace, attacker, getNextSpace)))
      }
      case RemovePiece(executor) if executor == attacker => {
        List(RemoveTrigger(this))
      }
    }
  }

  private def mapNextSpaceToEffect(action: Space => Effect): List[Effect] = {
    // TODO: This can be written much nicer in Scala
    var newEffects: List[Effect] = List()
    var nextSpace: Option[Space] = getNextSpace(targetedSpace)
    while (nextSpace.isDefined) {
      newEffects = newEffects.+:(action(nextSpace.get))
      nextSpace = getNextSpace(nextSpace.get)
    }
    newEffects
  }
}
