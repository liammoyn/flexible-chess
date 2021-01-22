package turn.triggers

import board.Coordinate
import turn.initiatingactions.Move
import turn.sideeffects.KillAllEnemies
import turn.{InitiatingAction, SideEffect, Trigger}

case class KillEnemyOnSpace(thisCoordinate: Coordinate) extends Trigger {
  override def reaction(initiatingAction: InitiatingAction): List[SideEffect] = {
    initiatingAction match {
      case Move(executor, coordinate) if coordinate == thisCoordinate => {
        List(KillAllEnemies(friendlyTeam = executor.team))
      }
      case _ => List()
    }
  }
}
