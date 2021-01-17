package gamerunner

import board.{BoardState, Coordinate, Space}
import piece.PieceType.PieceType
import piece.{King, Pawn, Piece, PieceType}
import team.Team.Team
import turn.InitiatingAction
import turn.initiatingactions.{Castle, Move, PawnTwoStepMove, TransformPawn}

import scala.io.StdIn

class HumanNotationPlayer extends Player {

  override def takeTurn(boardState: BoardState, team: Team): InitiatingAction = {
    val moveString = StdIn.readLine()
    val maybeMove = getMoveFromString(moveString, boardState, team)

    if (maybeMove.isEmpty) {
      System.out.println("Illegal move, play again")
      takeTurn(boardState, team)
    }
    maybeMove.get
  }

  private def getMoveFromString(str: String, boardState: BoardState, team: Team): Option[InitiatingAction] = {
    str match {
      case "OO" => getCastleAction(boardState, team, castleRight = true)
      case "OOO" => getCastleAction(boardState, team, castleRight = false)
      case _ => getNonCastleMoveFromString(str, boardState, team)
    }
  }

  private def getNonCastleMoveFromString(str: String, boardState: BoardState, team: Team): Option[InitiatingAction] = {
    val pieceType: Option[PieceType] = PieceType.fromChar(str.charAt(0))

    val fromColumn = colFromChar(str.charAt(1))
    val fromRow = rowFromChar(str.charAt(2))
    val from: Option[Coordinate] = fromColumn.flatMap(c => fromRow.flatMap(r => Some(Coordinate(r, c))))


    val toColumn = colFromChar(str.charAt(3))
    val toRow = rowFromChar(str.charAt(4))
    val to: Option[Coordinate] = toColumn.flatMap(c => toRow.flatMap(r => Some(Coordinate(r, c))))

    val hasTransform = str.contains("=")
    val transformTo: Option[PieceType] = if (hasTransform) {
      val transformString = str.split("=")(2)
      if (transformString.length == 1) {
        PieceType.fromChar(transformString.charAt(0))
      } else None
    } else None

    if (pieceType.isEmpty || from.isEmpty || to.isEmpty || (hasTransform && transformTo.isEmpty)) {
      None
    } else {
      getAction(boardState, pieceType.get, from.get, to.get, transformTo)
    }
  }

  private def getAction(boardState: BoardState, pieceType: PieceType, from: Coordinate, to: Coordinate, transform: Option[PieceType]): Option[InitiatingAction] = {
    boardState.getSpace(from)
      .flatMap(space => space.occupiers.find(p => p.pieceType == pieceType))
      .map(piece => {
        if (transform.isDefined && pieceType == PieceType.PAWN) {
          TransformPawn(piece.asInstanceOf[Pawn], PieceType.toPiece(transform.get, piece))
        } else if (pieceType == PieceType.PAWN && Math.abs(to.row - from.row) == 2) {
          PawnTwoStepMove(executor = piece.asInstanceOf[Pawn])
        } else {
          Move(piece, to)
        }
      })
  }

  private def getCastleAction(boardState: BoardState, team: Team, castleRight: Boolean): Option[Castle] = {
    val rookColumn = if (castleRight) boardState.cols - 1 else 0
    val pieceClause = (pieceType: PieceType) => (piece: Piece) => piece.pieceType == pieceType && piece.team == team

    val maybeKingSpace: Option[Space] = boardState.getAllSpaces
      .find(_.occupiers
        .exists(pieceClause(PieceType.KING)))

    maybeKingSpace.flatMap(kingSpace => {
      boardState.getSpace(Coordinate(kingSpace.coordinate.row, rookColumn)).flatMap(rookSpace => {
        kingSpace.occupiers.find(pieceClause(PieceType.KING)).flatMap(kingPiece => {
          rookSpace.occupiers.find(pieceClause(PieceType.ROOK)).map(rookPiece => {
            Castle(kingPiece.asInstanceOf[King], rookPiece)
          })
        })
      })
    })
  }

  private def rowFromChar(char: Char): Option[Int] = {
    if (char >= '1' && char <= '8') {
      Some(7 - (char - '1'))
    } else {
      None
    }
  }

  private def colFromChar(char: Char): Option[Int] = {
    if (char >= 'a' && char <= 'z') {
      Some(char - 'a')
    } else {
      None
    }
  }
}
