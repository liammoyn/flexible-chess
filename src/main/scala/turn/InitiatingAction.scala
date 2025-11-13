package turn

import gamerunner.GameState
import piece.Piece

trait InitiatingAction {
  def executor: Piece

  def initiate(gameState: GameState): List[Effect]
}
