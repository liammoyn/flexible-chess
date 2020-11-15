package turn.triggers

import board.{BoardState, Coordinate}
import piece.{Pawn, Queen}
import turn.actions.{Kill, Move, Transform}
import turn.{InitiatingAction, SideAction, Trigger}

class TransformPawnAtEnd extends Trigger{
  override def reaction(initiatingAction: InitiatingAction, currentBoardState: BoardState): List[SideAction] = {
    initiatingAction match {
      case Move(pawn: Pawn, to: Coordinate) => {
        if (to.row == 0 || to.row == currentBoardState.rows - 1) {
          val p: Pawn = pawn.copyPiece(pawn)
          List(Transform(pawn, )
        }
      }
      case _ => List()
    }
  }
}
