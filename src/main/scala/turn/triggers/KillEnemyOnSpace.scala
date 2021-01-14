package turn.triggers

import board.Coordinate
import turn.initiatingactions.Move
import turn.sideeffects.KillAllEnemies
import turn.{InitiatingAction, SideEffect, Trigger}

class KillEnemyOnSpace(val thisCoordinate: Coordinate) extends Trigger {

  override def reaction(initiatingAction: InitiatingAction): List[SideEffect] = {
    initiatingAction match {
      case Move(executer, coordinate) if coordinate == thisCoordinate => {
        List(KillAllEnemies(friendlyTeam = executer.team))
      }
      case _ => List()
    }
  }
}
