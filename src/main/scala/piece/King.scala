package piece

import board.{BoardState, Coordinate}
import piece.PieceType.{KING, PieceType, ROOK}
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.{Castle, Move}
import util.PieceId

class King private(id: PieceId, pieceType: PieceType, team: Team, hasMoved: Boolean) extends Piece(id, pieceType, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    val normalMoves: List[Move] = List(
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

    val castles: List[Castle] = if (!this.hasMoved) {
      currentBoard.pieces.keys
        .filter(piece => piece.team == this.team && !piece.hasMoved && piece.pieceType == ROOK)
        .map(piece => Castle(this, piece))
        .toList
    } else {
      List()
    }

    normalMoves ::: castles
  }
}

object King {
  def apply(team: Team): King = new King(PieceId(), KING, team, false)

  def apply(piece: Piece, samePiece: Boolean = true): King = new King(
    if (samePiece) piece.id else PieceId(),
    KING,
    piece.team,
    piece.hasMoved
  )
}
