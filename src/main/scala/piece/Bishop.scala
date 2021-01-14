package piece

import board.{BoardState, Coordinate}
import piece.PieceType.{BISHOP, PieceType}
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.Move
import util.PieceId

import scala.collection.mutable.ListBuffer

class Bishop private(id: PieceId, pieceType: PieceType, team: Team, hasMoved: Boolean) extends Piece(id, pieceType, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    val leftUp: ListBuffer[Coordinate] = directionList(leftUpGen(at), leftUpGen, currentBoard)
    val leftDown: ListBuffer[Coordinate] = directionList(leftDownGen(at), leftDownGen, currentBoard)
    val rightUp: ListBuffer[Coordinate] = directionList(rightUpGen(at), rightUpGen, currentBoard)
    val rightDown: ListBuffer[Coordinate] = directionList(rightDownGen(at), rightDownGen, currentBoard)

    List.concat(leftUp, leftDown, rightUp, rightDown)
      .map(cord => {
        Move(this, cord)
      })
  }
}

object Bishop {
  def apply(team: Team): Bishop = new Bishop(PieceId(), BISHOP, team, false)

  def apply(piece: Piece, samePiece: Boolean = true): Bishop = new Bishop(
    if (samePiece) piece.id else PieceId(),
    BISHOP,
    piece.team,
    piece.hasMoved
  )
}
