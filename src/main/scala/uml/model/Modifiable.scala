package uml.model

import uml.model.Modifiers.Modifier

trait Modifiable {
  def modifiers: List[Modifier]

  def isVisible: Boolean = modifiers.contains(Modifiers.Public)
}