package piece

object PieceType extends Enumeration {
  type PieceType = Value
  val KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN = Value

  def fromChar(char: Char): Option[PieceType] = {
    char.toUpper match {
      case 'B' => Some(BISHOP)
      case 'K' => Some(KING)
      case 'N' => Some(KNIGHT)
      case 'P' => Some(PAWN)
      case 'Q' => Some(QUEEN)
      case 'R' => Some(ROOK)
      case _ => None
    }
  }

  def toPiece(pieceType: PieceType, copy: Piece): Piece = {
    pieceType match {
      case KING => King(copy)
      case QUEEN => Queen(copy)
      case BISHOP => Bishop(copy)
      case KNIGHT => Knight(copy)
      case ROOK => Rook(copy)
      case PAWN => Pawn(copy)
    }
  }
}
