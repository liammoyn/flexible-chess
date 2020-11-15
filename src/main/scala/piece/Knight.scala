package piece
import board.{BoardState, Coordinate}
import team.Team.Team
import turn.{Action, InitiatingAction}
import turn.actions.Move
import util.PieceId

class Knight private (team: Team, hasMoved: Boolean, id: PieceId) extends Piece(id, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    List(
      Coordinate(at.row + 2, at.col + 1),
      Coordinate(at.row + 2, at.col - 1),
      Coordinate(at.row + 1, at.col + 2),
      Coordinate(at.row + 1, at.col - 2),
      Coordinate(at.row - 2, at.col + 1),
      Coordinate(at.row - 2, at.col - 1),
      Coordinate(at.row - 1, at.col + 2),
      Coordinate(at.row - 1, at.col - 2),
    )
      .filter(cord => currentBoard.isCoordinateEmpty(cord) || currentBoard.isEnemyAtCoordinate(cord, team))
      .map(cord => {
        Move(this, cord)
      })
  }

  def move(): Knight = {
    new Knight(this.team, true, this.id)
  }
}

object Knight {
  def apply(team: Team): Knight = new Knight(team, false, PieceId())
}
