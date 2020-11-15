package piece

import board.{BoardState, Coordinate}
import team.Team.Team
import turn.{Action, InitiatingAction}
import turn.actions.Move
import util.PieceId

import scala.collection.mutable.ListBuffer

class Rook private (team: Team, hasMoved: Boolean, id: PieceId) extends Piece(id, team, hasMoved) {

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

  def move(): Rook = {
    new Rook(this.team, true, this.id)
  }
}

object Rook {
  def apply(team: Team): Rook = new Rook(team, false, PieceId())
}
