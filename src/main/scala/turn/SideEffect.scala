package turn

import board.Space

trait SideEffect {
  def execute(space: Space): Space
}
