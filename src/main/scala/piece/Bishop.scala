package piece

import board.{BoardState, Coordinate}
import team.Team.Team
import turn.{Action, InitiatingAction}
import turn.actions.Move
import util.PieceId

import scala.collection.mutable.ListBuffer

class Bishop private (team: Team, hasMoved: Boolean, id: PieceId) extends Piece(id, team, hasMoved) {

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

  def move(): Bishop = {
    new Bishop(this.team, true, this.id)
  }
}

object Bishop {
  def apply(team: Team): Bishop = new Bishop(team, false, PieceId())
}
