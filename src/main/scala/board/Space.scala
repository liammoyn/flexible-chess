package board

import piece.Piece

class Space private (val coordinate: Coordinate, val occupiers: List[Piece]) {
  def removePiece(piece: Piece): Space = {
    this.createCopy(this.occupiers.filterNot(p => p.equals(piece)))
  }

  def addPiece(piece: Piece): Space = {
    this.createCopy(piece :: this.occupiers)
  }

//  def updateFromAction(initiatingAction: InitiatingAction): Space = {
//    val allEffects: List[SideEffect] = this.activeTriggers.flatMap(trigger => trigger.reaction(initiatingAction))
//    // TODO: Undefined order of execution for effects
//    allEffects.foldLeft(this)((space, sideEffect) => sideEffect.execute(space))
//  }

  def createCopy(occupiers: List[Piece]): Space =
    new Space(this.coordinate, occupiers)
}

object Space {
  def apply(at: Coordinate): Space = new Space(at, List())
  def apply(at: Coordinate, piece: Piece): Space = new Space(at, List(piece))
}
