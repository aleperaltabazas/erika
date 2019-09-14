package uml.model

import uml.model.Modifiers.Modifier

trait Modifiable {
  def modifiers: List[Modifier]

  def isVisible: Boolean = is(Modifiers.Public)

  def is(modifier: Modifier): Boolean = modifiers.contains(modifier)
}