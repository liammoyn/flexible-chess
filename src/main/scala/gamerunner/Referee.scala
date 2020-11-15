package gamerunner

import board.{BoardState, Coordinate, Space}
import main.Viewer
import piece.{King, Piece}
import team.Team
import team.Team.Team
import turn.{Action, ActionManager, ActionResponder, InitiatingAction}

import scala.collection.mutable.ListBuffer

class Referee(private var boardState: BoardState) {
  private val actionManager: ActionManager = new ActionManager(List())

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
    val playerTurn: Action = player.takeTurn(boardState, playingTeam)

    // Get all Effects
    // val allActions = getAllActionsFromPlayerTurn(playerTurn)
    val allActions = playerTurn :: actionManager.respondToAction(playerTurn, boardState)

    /* TODO: Make sure move is valid */

    // Apply all Effects
    applyActions(allActions, boardState)
  }

  private def getAllActionsFromPlayerTurn(initiatingAction: Action): List[Action] = {
    def getResponders(action: Action): List[ActionResponder] = {
      // TODO: who is asked to respond to a particular action?
      ???
    }
    def getResponseActions(action: Action): List[Action] = {
      getResponders(action).flatMap(responder => responder.respondToAction(action))
    }
    def getAllActions(initiatorAction: Action): List[Action] = {
      initiatorAction :: getResponseActions(initiatorAction).flatMap(nextInitiatorAction => getAllActions(nextInitiatorAction))
    }
    getAllActions(initiatingAction)
  }

  private def applyActions(allActions: List[Action], boardState: BoardState): BoardState = {
    allActions
      .sortBy(action => action.executionOrder)
      .foldLeft(boardState)((board, action) => {
        action.execute(board)
      })
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
      .foldLeft(ListBuffer[Action]())((acc, cur) => {
        cur.occupiers.foreach(piece => {
          acc.addAll(piece.validMoves(cur.coordinate, boardState))
        })
        acc
      })
      .toList
  }
}
