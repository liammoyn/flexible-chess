package gamerunner

import board.{BoardState, Space}
import main.Viewer
import piece.{King, Piece}
import team.Team
import team.Team.Team
import turn.InitiatingAction

import scala.collection.mutable.ListBuffer

class Referee(private var boardState: BoardState) {
  /**
   * TODO: Behavior undefined when players.size > Team.values.size
   */
  def playGame(players: List[Player]): Player = {
    val teams: Map[Team, Player] = players
      .zipWithIndex
      .map(pi => (Team.values.toList(pi._2 % Team.values.size), pi._1))
      .toMap
    val turnList: List[Team] = Team.values.toList
    var turnIndex: Int = 0
    while (getWinner().isEmpty) {
      println(Viewer.getBoardString(boardState))
      val playerTurn = teams(turnList(turnIndex))
      this.boardState = playTurn(this.boardState, playerTurn, turnList(turnIndex))
      turnIndex = (turnIndex + 1) % turnList.size
    }
    teams(getWinner().get)
  }

  private def playTurn(boardState: BoardState, player: Player, playingTeam: Team): BoardState = {
    val playerTurn: InitiatingAction = player.takeTurn(boardState, playingTeam)

    // Update spaces based on IA
    val newSpaces: Iterable[Space] = playerTurn.execute(boardState)
      .getAllSpaces
      .map(space => space.updateFromAction(playerTurn))

    /* TODO: Make sure move is valid */

    boardState.updateSpaces(newSpaces)
  }

  // TODO: This needs to be made more general / flexible
  private def getWinner(): Option[Team] = {
    val remainingKings: List[Piece] = boardState.getAllSpaces
      .foldLeft(List[Piece]())((acc, cur) => acc.:::(cur
        .occupiers
        .filter((p: Piece) => {
          p match {
            case p: King => true
            case p => false
          }
        })
      ))
    if (remainingKings.size == 1) {
      Some(remainingKings.head.team)
    } else {
      None
    }
  }
}

object Referee {
  def validMoves(boardState: BoardState, turn: Team): List[InitiatingAction] = {
    boardState.getAllSpaces
      .filter((space) => space.occupiers.exists(p => p.team.equals(turn)))
      .foldLeft(ListBuffer[InitiatingAction]())((acc, cur) => {
        cur.occupiers.foreach(piece => {
          acc.addAll(piece.validMoves(cur.coordinate, boardState))
        })
        acc
      })
      .toList
  }
}
