package board

case class Coordinate(row: Int, col: Int) {
  def alongRow(rowOffset: Int): Coordinate = {
    along(rowOffset, 0)
  }

  def alongColumn(columnOffset: Int): Coordinate = {
    along(0, columnOffset)
  }

  def along(rowOffset: Int, columnOffset: Int): Coordinate = {
    Coordinate(this.row + rowOffset, this.col + columnOffset)
  }
}
