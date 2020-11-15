package turn

import board.BoardState
import turn.actions.{Kill, Move}

class ActionManager(val responders: List[ActionResponder]) {
  def getResponseActions(action: InitiatingAction, boardState: BoardState): List[SideAction] = {
    action match {
      case move: Move => {
        boardState.getSpace(move.to)
          .get
          .occupiers
          .filterNot(p => p.team.equals(move.executer.team))
          .map(p => Kill(p))
      }
      case _ => List()
    }
  }

  def applyActions(actions: List[Action], startingState: BoardState): Option[BoardState] = {
    actions
      .sortBy(action => action.executionOrder)
      .foldLeft[Option[BoardState]](Some(startingState))((maybeBoardState, action) => {
        maybeBoardState.flatMap(boardState => action.execute(boardState))
      })
  }

  def applyActionsWithErrorMessage(actions: List[Action], startingState: BoardState): Either[BoardState, String] = {
    actions
      .sortBy(action => action.executionOrder)
      .foldLeft[Either[BoardState, String]](Left(startingState))((acc, action) => {
        acc match {
          case Left(boardState) => action.execute(boardState).toLeft(s"${action} has failed on ${boardState}")
          case Right(message) => Right(message)
        }
      })
  }
}
