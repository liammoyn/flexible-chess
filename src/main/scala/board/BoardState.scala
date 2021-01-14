package board

import piece.Piece
import team.Team.Team

class BoardState(val rows: Int, val cols: Int, private val spaces: Map[Coordinate, Space]) {
  val pieces: Map[Piece, Coordinate] = this.spaces
    .filterNot(cs => cs._2.occupiers.isEmpty)
    .foldLeft(Map.newBuilder[Piece, Coordinate])((acc, entry) => {
      acc.addAll(entry._2.occupiers.map(piece => (piece, entry._1)))
    })
    .result()

  def updateSpaces(updates: Iterable[Space]): BoardState = {
    new BoardState(rows, cols, spaces ++ updates.map(space => (space.coordinate, space)))
  }

  def updateSpace(space: Space): BoardState = {
    new BoardState(rows, cols, spaces.+((space.coordinate, space)))
  }

  def getAllSpaces: Iterable[Space] = this.spaces.values
  def getSpace(piece: Piece): Option[Space] = this.pieces.get(piece).flatMap(cord => this.spaces.get(cord))
  def getSpace(coordinate: Coordinate): Option[Space] = this.spaces.get(coordinate)
  def getCoordinate(piece: Piece): Option[Coordinate] = this.pieces.get(piece)
  def getPiece(coordinate: Coordinate): Option[Piece] = this.spaces.get(coordinate).flatMap(space => space.occupiers.headOption)

  def isCoordinateValid(at: Coordinate): Boolean = spaces.contains(at)
  def isCoordinateEmpty(at: Coordinate): Boolean = spaces.get(at).exists(space => space.occupiers.isEmpty)
  def isEnemyAtCoordinate(at: Coordinate, friendlyTeam: Team): Boolean = spaces.get(at).exists(space => space.occupiers.exists(piece => piece.team != friendlyTeam))

  // TODO: Probably add some team logic here?
  def isAtEndOfBoard(coordinate: Coordinate): Boolean = {
    coordinate.row == this.rows - 1 || coordinate.row == 0
  }

  def print() {
    for ((k, v) <- spaces) {
      println(k + " = " + v)
    }
  }
}

object BoardState {
  def apply(rows: Int, cols: Int): BoardState = new BoardState(rows, cols, (for(i <- 0 to rows; j <- 0 to cols) yield (Coordinate(i, j), Space(Coordinate(i, j)))).toMap)
}
