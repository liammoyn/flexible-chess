package turn

import board.Coordinate

trait Trigger {
  /**
   * When one of these spaces is updated, this trigger should be checked
   */
  def watchedCoordinates: Iterable[Coordinate]

  /**
   * TODO: Is there anyway to prevent infinite loops?
   * Given an effect return a list of effects triggered because of it
   */
  def reaction(effect: Effect): List[Effect]
}
