package turn.effects

import board.BoardState
import turn.{Effect, Trigger}

case class AddTrigger(trigger: Trigger) extends Effect {
  override def execute(boardState: BoardState): BoardState = {
    boardState.addTrigger(trigger)
  }
}
