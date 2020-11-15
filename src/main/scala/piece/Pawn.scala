package piece

import board.{BoardState, Coordinate}
import team.Team
import team.Team.Team
import turn.{Action, InitiatingAction}
import turn.actions.Move
import util.PieceId

class Pawn private (team: Team, hasMoved: Boolean, id: PieceId) extends Piece(id, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    val teamDirection = if (team.equals(Team.BLACK)) 1 else -1

    val positionMoves: List[InitiatingAction] = {
      var coords = List(Coordinate(at.row + teamDirection, at.col))
      if (!hasMoved) {
        coords = coords.::(Coordinate(at.row + teamDirection * 2, at.col))
      }
      coords
    }
      .filter(cord => currentBoard.isCoordinateEmpty(cord))
      .map(cord => Move(this, cord))


    val attackMoves: List[InitiatingAction] = List(
      Coordinate(at.row + teamDirection, at.col - 1),
      Coordinate(at.row + teamDirection, at.col + 1)
    )
      .filter(cord => currentBoard.isEnemyAtCoordinate(cord, team))
      .map(cord => {
        Move(this, cord)
      })

    attackMoves.:::(positionMoves)
  }

  def move(): Pawn = {
    new Pawn(this.team, true, this.id)
  }

  override def copyPiece(piece: Piece): Pawn.this.type = {
    new Pawn(piece.team, piece.hasMoved, piece.id)
  }
}

object Pawn {
  def apply(team: Team): Pawn = new Pawn(team, false, PieceId())
}
