package board

case class Coordinate(row: Int, col: Int) {
  def alongRow(columnOffset: Int): Coordinate = {
    along(0, columnOffset)
  }

  def alongColumn(rowOffset: Int): Coordinate = {
    along(rowOffset, 0)
  }

  def along(rowOffset: Int, columnOffset: Int): Coordinate = {
    Coordinate(this.row + rowOffset, this.col + columnOffset)
  }
}
