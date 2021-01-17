package main

import board.{BoardState, Coordinate, Space}
import gamerunner.{ComputerPlayer, HumanNotationPlayer, Referee}
import piece.{Bishop, King, Knight, Pawn, Queen, Rook}
import team.Team

import scala.collection.mutable

object Main {
  def main(args: Array[String]): Unit = {
    val b: BoardState = basicBoard()
    val players = List(new HumanNotationPlayer(), new ComputerPlayer())
    val referee = new Referee(b)
    print(referee.playGame(players))
  }

  def basicBoard(): BoardState = {
    val spaces: mutable.ListBuffer[Space] = mutable.ListBuffer()
    spaces.addAll(for (row <- 0 to 7; col <- 0 to 7) yield Space(Coordinate(row, col)))
    spaces.addAll(List(
      Space(Coordinate(0, 0), Rook(Team.BLACK)),
      Space(Coordinate(0, 1), Knight(Team.BLACK)),
      Space(Coordinate(0, 2), Bishop(Team.BLACK)),
      Space(Coordinate(0, 3), King(Team.BLACK)),
      Space(Coordinate(0, 4), Queen(Team.BLACK)),
      Space(Coordinate(0, 5), Bishop(Team.BLACK)),
      Space(Coordinate(0, 6), Knight(Team.BLACK)),
      Space(Coordinate(0, 7), Rook(Team.BLACK)),
    ))
    spaces.addAll(for (col <- 0 to 7) yield Space(Coordinate(1, col), Pawn(Team.BLACK)))
    spaces.addAll(List(
      Space(Coordinate(7, 0), Rook(Team.WHITE)),
      Space(Coordinate(7, 1), Knight(Team.WHITE)),
      Space(Coordinate(7, 2), Bishop(Team.WHITE)),
      Space(Coordinate(7, 3), Queen(Team.WHITE)),
      Space(Coordinate(7, 4), King(Team.WHITE)),
      Space(Coordinate(7, 5), Bishop(Team.WHITE)),
      Space(Coordinate(7, 6), Knight(Team.WHITE)),
      Space(Coordinate(7, 7), Rook(Team.WHITE)),
    ))
    spaces.addAll(for (col <- 0 to 7) yield Space(Coordinate(6, col), Pawn(Team.WHITE)))

    new BoardState(8, 8, spaces.toList.map(sp => (sp.coordinate, sp)).toMap)
  }
}
