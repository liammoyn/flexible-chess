package board

import piece.Piece
import team.Team.Team
import turn.{Effect, Trigger}

class BoardState(val rows: Int,
                 val cols: Int,
                 private val spaces: Map[Coordinate, Space],
                 private val triggers: Set[Trigger]) {

  val pieces: Map[Piece, Coordinate] = this.spaces
    .filterNot(cs => cs._2.occupiers.isEmpty)
    .foldLeft(Map.newBuilder[Piece, Coordinate])((acc, entry) => {
      acc.addAll(entry._2.occupiers.map(piece => (piece, entry._1)))
    })
    .result()

  val coordinateTriggers: Map[Coordinate, Iterable[Trigger]] = this.triggers
    .flatMap(t => t.watchedCoordinates.map(i => (i, t)))
    .groupMap(_._1)(_._2)

  def updateSpaces(updates: Iterable[Space]): BoardState = {
    new BoardState(rows, cols, spaces ++ updates.map(space => (space.coordinate, space)), ???)
  }

  // TODO: Update triggers according to spaces?
  def updateSpace(space: Space, cause: Effect): BoardState = {
    val activatedTriggers: Iterable[Trigger] = coordinateTriggers(space.coordinate)
    val updatedSpaceBoardState = new BoardState(rows, cols, spaces + ((space.coordinate, space)), triggers)

    activatedTriggers.foldLeft(updatedSpaceBoardState)((triggerBoardState, trigger) =>
      trigger.reaction(cause).foldLeft(triggerBoardState)((effectBoardState, effect) =>
        effect.execute(effectBoardState))) // Can very easily go into infinite loop here
  }

  def addTrigger(trigger: Trigger): BoardState = {
    new BoardState(rows, cols, spaces, triggers + trigger)
  }

  def removeTrigger(trigger: Trigger): BoardState = {
    new BoardState(rows, cols, spaces, triggers - trigger)
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
  def apply(rows: Int, cols: Int): BoardState = new BoardState(rows, cols, (for(i <- 0 to rows; j <- 0 to cols) yield (Coordinate(i, j), Space(Coordinate(i, j)))).toMap, Set())
}
