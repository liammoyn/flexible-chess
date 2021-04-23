package turn.effects

import board.BoardState
import piece.Piece
import turn.Effect

case class Kill(pieces: Iterable[Piece]) extends Effect {
  val pieceSet: Set[Piece] = Set.from(this.pieces)

  override def execute(boardState: BoardState): BoardState = {
    boardState.pieces.keySet
      .intersect(pieceSet)
      .foldLeft[Option[BoardState]](Some(boardState))((maybeBoardState, piece) => {
        maybeBoardState.flatMap(bs => {
          val maybeUpdatedSpace = bs.getSpace(piece).map(space => space.removePiece(piece))
          maybeUpdatedSpace.map(updatedSpace => bs.updateSpace(updatedSpace, this))
        })
      }).get
  }
}
