package team

object Team extends Enumeration {
  type Team = Value
  val WHITE, BLACK = Value

  def getDirection(team: Team): Int = {
    if (team.equals(Team.BLACK)) 1 else -1
  }
}
