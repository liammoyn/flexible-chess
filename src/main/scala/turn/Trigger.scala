package turn

import board.Coordinate

trait Trigger {

  /**
   * TODO: Is there anyway to prevent infinite loops?
   * Given an effect return a list of effects triggered because of it
   */
  def reaction(effect: Effect): List[Effect]
}
