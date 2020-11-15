package piece

import board.{BoardState, Coordinate}
import team.Team.Team
import turn.{Action, InitiatingAction}
import util.PieceId

import scala.collection.mutable.ListBuffer

abstract class Piece(val id: PieceId, val team: Team, val hasMoved: Boolean) {

  def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction]

  def copyPiece(piece: Piece): this.type

  protected def directionList(cur: Coordinate, nextCoordinate: (Coordinate) => Coordinate, boardState: BoardState): ListBuffer[Coordinate] = {
    if (boardState.isEnemyAtCoordinate(cur, team)) {
      ListBuffer(cur)
    } else if (boardState.isCoordinateEmpty(cur)) {
      directionList(nextCoordinate(cur), nextCoordinate, boardState).addOne(cur)
    } else {
      ListBuffer()
    }
  }

  protected val leftGen: Coordinate => Coordinate = cur => Coordinate(cur.row, cur.col - 1)
  protected val rightGen: Coordinate => Coordinate = cur => Coordinate(cur.row, cur.col + 1)
  protected val upGen: Coordinate => Coordinate = cur => Coordinate(cur.row - 1, cur.col)
  protected val downGen: Coordinate => Coordinate = cur => Coordinate(cur.row + 1, cur.col)
  protected val leftUpGen: Coordinate => Coordinate = cur => Coordinate(cur.row - 1, cur.col - 1)
  protected val rightUpGen: Coordinate => Coordinate = cur => Coordinate(cur.row - 1, cur.col + 1)
  protected val leftDownGen: Coordinate => Coordinate = cur => Coordinate(cur.row + 1, cur.col - 1)
  protected val rightDownGen: Coordinate => Coordinate = cur => Coordinate(cur.row + 1, cur.col + 1)


  override def hashCode(): Int = id.id
  override def equals(obj: Any): Boolean = {
    obj match {
      case piece: Piece => piece.id.equals(this.id)
      case _ => false
    }
  }
}
