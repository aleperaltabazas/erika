package uml.builder

import uml.model.Modifiers
import uml.model.Modifiers.Modifier

trait Builder {
  def modifiers: List[Modifier]

  def effectiveModifiers: List[Modifier] = {
    if (declaresVisibility) modifiers
    else modifiers :+ Modifiers.PackagePrivate
  }

  private def declaresVisibility: Boolean = {
    modifiers.contains(Modifiers.Public) || modifiers.contains(Modifiers.Protected) || modifiers.contains(Modifiers.Private)
  }
}