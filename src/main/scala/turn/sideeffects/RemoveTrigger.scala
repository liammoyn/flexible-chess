package turn.sideeffects

import board.Space
import turn.{SideEffect, Trigger}

case class RemoveTrigger(trigger: Trigger) extends SideEffect {
  override def execute(space: Space): Space = {
    space.removeTrigger(trigger)
  }
}
