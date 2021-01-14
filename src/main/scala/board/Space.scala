package board

import piece.Piece
import turn.{InitiatingAction, SideEffect, Trigger}

class Space private (val coordinate: Coordinate, val occupiers: List[Piece], val activeTriggers: List[Trigger]) {
  def removePiece(piece: Piece): Space = {
    this.createCopy(this.occupiers.filterNot(p => p.equals(piece)), this.activeTriggers)
  }

  def addPiece(piece: Piece): Space = {
    this.createCopy(piece :: this.occupiers, this.activeTriggers)
  }

  def removeTrigger(trigger: Trigger): Space = {
    this.createCopy(this.occupiers, this.activeTriggers.filterNot(t => t.equals(trigger)))
  }

  def addTrigger(trigger: Trigger): Space = {
    this.createCopy(this.occupiers, trigger :: this.activeTriggers)
  }

  def updateFromAction(initiatingAction: InitiatingAction): Space = {
    val allEffects: List[SideEffect] = this.activeTriggers.flatMap(trigger => trigger.reaction(initiatingAction))
    // TODO: Undefined order of execution for effects
    allEffects.foldLeft(this)((space, sideEffect) => sideEffect.execute(space))
  }

  def createCopy(occupiers: List[Piece], activeTriggers: List[Trigger]): Space =
    new Space(this.coordinate, occupiers, activeTriggers)
}

object Space {
  def apply(at: Coordinate): Space = new Space(at, List(), List())
  def apply(at: Coordinate, piece: Piece): Space = new Space(at, List(piece), List())
}
