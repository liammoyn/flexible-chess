package gamerunner
import board.{BoardState, Coordinate}
import main.Viewer
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.Move

import scala.io.StdIn

class HumanPlayer extends Player {

  override def takeTurn(boardState: BoardState, team: Team): InitiatingAction = {
    var moveString = StdIn.readLine()
    while (!isValidMoveString(moveString)) {
      moveString = StdIn.readLine()
    }
    val (colFS, rowFS, colTS, rowTS) = destructMoveString(moveString)
    val fromCoordinate = Coordinate(Viewer.rowMarkers.indexOf(rowFS), Viewer.colMarkers.indexOf(colFS))
    val toCoordinate = Coordinate(Viewer.rowMarkers.indexOf(rowTS), Viewer.colMarkers.indexOf(colTS))
    val movingPiece = boardState.getPiece(fromCoordinate).get
    Move(movingPiece, toCoordinate)
  }

  private def isValidMoveString(str: String): Boolean = {
    if (str.length != 4) {
      return false
    }
    val (colFS, rowFS, colTS, rowTS) = destructMoveString(str)
    Viewer.colMarkers.contains(colFS) &&
      Viewer.rowMarkers.contains(rowFS) &&
      Viewer.colMarkers.contains(colTS) &&
      Viewer.rowMarkers.contains(rowTS)
  }

  def destructMoveString(str: String): (String, String, String, String) = {
    str.split("", 4) match {
      case Array(a: String, b: String, c: String, d: String) => (a, b, c, d)
      case _ => throw new IllegalArgumentException()
    }
  }
}
