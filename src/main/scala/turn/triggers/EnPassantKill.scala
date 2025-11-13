package turn.triggers

import board.Coordinate
import piece.Pawn
import turn.effects.{AddPiece, AdvanceTurn, RemovePiece, RemoveTrigger}
import turn.{Effect, Trigger}

case class EnPassantKill(killCoordinate: Coordinate, target: Pawn) extends Trigger {
  override def reaction(initiatingEffect: Effect): List[Effect] = {
    initiatingEffect match {
      case AddPiece(executor, coordinate) if (coordinate == killCoordinate && executor.team != target.team) => {
        List(RemovePiece(target))
      }
      case AdvanceTurn(nextTurn) if nextTurn == target.team => {
        List(RemoveTrigger(this))
      }
      case _ => List()
    }
  }
}
