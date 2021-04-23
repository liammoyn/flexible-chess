package turn.effects

import board.BoardState
import turn.{Effect, Trigger}

case class RemoveTrigger(trigger: Trigger) extends Effect {
  override def execute(boardState: BoardState): BoardState = {
    boardState.removeTrigger(trigger)
  }
}
