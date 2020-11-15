package turn

import board.BoardState

trait Trigger {
  // Define given one action if it produces more actions
  def reaction(initiatingAction: InitiatingAction, currentBoardState: BoardState): List[SideAction]
}
