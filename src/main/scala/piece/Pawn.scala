package piece

import board.{BoardState, Coordinate}
import piece.PieceType.{PAWN, PieceType}
import team.Team
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.{Move, PawnTwoStepMove, TransformPawn}
import util.PieceId

class Pawn private(id: PieceId, pieceType: PieceType, team: Team, hasMoved: Boolean) extends Piece(id, pieceType, team, hasMoved) {
  override def validMoves(at: Coordinate, currentBoard: BoardState): List[InitiatingAction] = {
    def checkTransform(coordinate: Coordinate, otherwise: InitiatingAction): List[InitiatingAction] = {
      if (currentBoard.isAtEndOfBoard(coordinate)) {
        List(Queen(this), Bishop(this), Rook(this), Knight(this))
          .map(p => TransformPawn(this, p))
      } else {
        List(otherwise)
      }
    }

    val teamDirection = Team.getDirection(team)
    val nextStep: Coordinate = Coordinate(at.row + teamDirection, at.col)
    val twoStep: Coordinate = nextStep.alongColumn(teamDirection)

    val oneStepMoves: List[InitiatingAction] = if (currentBoard.isCoordinateEmpty(nextStep)) {
      checkTransform(nextStep, Move(this, nextStep))
    } else {
      List()
    }

    val twoStepMoves: List[InitiatingAction] = if (currentBoard.isCoordinateEmpty(twoStep)) {
      checkTransform(twoStep, PawnTwoStepMove(this))
    } else {
      List()
    }

    val attackMoves: List[InitiatingAction] = List(nextStep.alongRow(1), nextStep.alongRow(-1))
      .filter(cord => currentBoard.isEnemyAtCoordinate(cord, team))
      .flatMap(cord => checkTransform(cord, Move(this, cord)))

    attackMoves ::: oneStepMoves ::: twoStepMoves
  }
}

object Pawn {
  def apply(team: Team): Pawn = new Pawn(PieceId(), PAWN, team, false)

  def apply(piece: Piece, samePiece: Boolean = true): Pawn = new Pawn(
    if (samePiece) piece.id else PieceId(),
    PAWN,
    piece.team,
    piece.hasMoved
  )
}
