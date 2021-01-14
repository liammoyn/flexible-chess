package piece

import board.{BoardState, Coordinate}
import piece.PieceType.{PieceType, QUEEN}
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.Move
import util.PieceId

import scala.collection.mutable.ListBuffer

class Queen private(id: PieceId, pieceType: PieceType, team: Team, hasMoved: Boolean) extends Piece(id, pieceType, team, hasMoved) {

  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    val left: ListBuffer[Coordinate] = directionList(leftGen(at), leftGen, currentBoard)
    val right: ListBuffer[Coordinate] = directionList(rightGen(at), rightGen, currentBoard)
    val up: ListBuffer[Coordinate] = directionList(upGen(at), upGen, currentBoard)
    val down: ListBuffer[Coordinate] = directionList(downGen(at), downGen, currentBoard)
    val leftUp: ListBuffer[Coordinate] = directionList(leftUpGen(at), leftUpGen, currentBoard)
    val leftDown: ListBuffer[Coordinate] = directionList(leftDownGen(at), leftDownGen, currentBoard)
    val rightUp: ListBuffer[Coordinate] = directionList(rightUpGen(at), rightUpGen, currentBoard)
    val rightDown: ListBuffer[Coordinate] = directionList(rightDownGen(at), rightDownGen, currentBoard)

    List.concat(left, right, up, down, leftUp, leftDown, rightUp, rightDown)
      .map(cord => {
        Move(this, cord)
      })
  }
}

object Queen {
  def apply(team: Team): Queen = new Queen(PieceId(), QUEEN, team, false)

  def apply(piece: Piece, samePiece: Boolean = true): Queen = new Queen(
    if (samePiece) piece.id else PieceId(),
    QUEEN,
    piece.team,
    piece.hasMoved
  )
}
