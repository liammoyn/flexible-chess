package turn

trait Trigger {
  def reaction(initiatingAction: InitiatingAction): List[SideEffect]
}
