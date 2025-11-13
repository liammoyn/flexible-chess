package turn.triggers

import board.Coordinate
import piece.Piece
import turn.effects.{AddPiece, RemovePiece, RemoveTrigger}
import turn.initiatingactions.Move
import turn.{Effect, InitiatingAction, Trigger}

case class KillByEnemy(killCoordinate: Coordinate, target: Piece) extends Trigger {
  override def reaction(initiatingEffect: Effect): List[Effect] = {
    initiatingEffect match {
      case AddPiece(executor, at) if at == killCoordinate && executor.team != target.team => {
        List(RemovePiece(target))
      }
      case RemovePiece(executor) if executor == target => {
        List(RemoveTrigger(this))
      }
      case _ => List()
    }
  }
}
