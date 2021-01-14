package turn.sideeffects

import board.Space
import team.Team.Team
import turn.SideEffect

case class KillAllEnemies(friendlyTeam: Team) extends SideEffect {
  override def execute(space: Space): Space = {
    val newOccupiers = space.occupiers.filter(piece => piece.team != friendlyTeam)
    space.createCopy(newOccupiers, space.activeTriggers)
  }
}
