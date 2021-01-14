package piece
import board.{BoardState, Coordinate}
import piece.PieceType.{KNIGHT, PieceType}
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.Move
import util.PieceId

class Knight private(id: PieceId, pieceType: PieceType, team: Team, hasMoved: Boolean) extends Piece(id, pieceType, team, hasMoved) {

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
}

object Knight {
  def apply(team: Team): Knight = new Knight(PieceId(), KNIGHT, team, false)

  def apply(piece: Piece, samePiece: Boolean = true): Knight = new Knight(
    if (samePiece) piece.id else PieceId(),
    KNIGHT,
    piece.team,
    piece.hasMoved
  )
}
