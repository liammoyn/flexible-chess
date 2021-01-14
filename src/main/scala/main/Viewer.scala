package main

import board.{BoardState, Coordinate, Space}
import piece.{Bishop, King, Knight, Pawn, Queen, Rook}
import team.Team

object Viewer {
  val rowMarkers: List[String] = List("8", "7", "6", "5", "4", "3", "2", "1")
  val colMarkers: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H")

  def getBoardString(boardState: BoardState): String = {
    val grid: List[List[Space]] = List
      .tabulate(boardState.rows, boardState.cols)((row, col) => boardState.getSpace(Coordinate(row, col)).get)

    val ans = new StringBuilder()
    ans.append("  +" + "-+".repeat(grid.head.length) + "\n")
    for (i: Int <- grid.indices) {
      ans.append(s"${rowMarkers(i)} |")
      for (space: Space <- grid(i)) {
        ans.append(getSpaceString(space))
        ans.append("|")
      }
      ans.append("\n")
      ans.append("  +" + "-+".repeat(grid(i).length) + "\n")
    }
    ans.append("   " + colMarkers.mkString(" "))

    ans.toString()
  }

  def getSpaceString(space: Space): String = {
    space.occupiers match {
      case List(value, _*) => { // TODO: No way of showing two piece on same space rn
        value match {
          case x: Pawn => if (x.team == Team.BLACK) "p" else "P"
          case x: Rook => if (x.team == Team.BLACK) "r" else "R"
          case x: Knight => if (x.team == Team.BLACK) "n" else "N"
          case x: Bishop => if (x.team == Team.BLACK) "b" else "B"
          case x: Queen => if (x.team == Team.BLACK) "q" else "Q"
          case x: King => if (x.team == Team.BLACK) "k" else "K"
        }
      }
      case _ => " "
    }
  }
}
