package piece

import board.{BoardState, Coordinate}
import piece.PieceType.{PieceType, ROOK}
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.Move
import util.PieceId

import scala.collection.mutable.ListBuffer

class Rook private(id: PieceId, pieceType: PieceType, team: Team, hasMoved: Boolean) extends Piece(id, pieceType, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    val left: ListBuffer[Coordinate] = directionList(leftGen(at), leftGen, currentBoard)
    val right: ListBuffer[Coordinate] = directionList(rightGen(at), rightGen, currentBoard)
    val up: ListBuffer[Coordinate] = directionList(upGen(at), upGen, currentBoard)
    val down: ListBuffer[Coordinate] = directionList(downGen(at), downGen, currentBoard)

    List.concat(left, right, up, down)
      .map(cord => {
        Move(this, cord)
      })
  }
}

object Rook {
  def apply(team: Team): Rook = new Rook(PieceId(), ROOK, team, false)

  def apply(piece: Piece, samePiece: Boolean = true): Rook = new Rook(
    if (samePiece) piece.id else PieceId(),
    ROOK,
    piece.team,
    piece.hasMoved
  )
}
