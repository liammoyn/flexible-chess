package piece

import board.{BoardState, Coordinate}
import team.Team.Team
import turn.{Action, InitiatingAction}
import turn.actions.Move
import util.PieceId

class King private (team: Team, hasMoved: Boolean, id: PieceId) extends Piece(id, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    List(
      Coordinate(at.row + 1, at.col),
      Coordinate(at.row + 1, at.col + 1),
      Coordinate(at.row + 1, at.col - 1),
      Coordinate(at.row, at.col + 1),
      Coordinate(at.row, at.col - 1),
      Coordinate(at.row - 1, at.col),
      Coordinate(at.row - 1, at.col + 1),
      Coordinate(at.row - 1, at.col - 1),
    )
      .filter(cord => currentBoard.isCoordinateEmpty(cord) || currentBoard.isEnemyAtCoordinate(cord, team))
      .map(cord => {
        Move(this, cord)
      })
  }

  def move(): King = {
    new King(this.team, true, this.id)
  }
}

object King {
  def apply(team: Team): King = new King(team, false, PieceId())
}
