package uml.model.methods

import uml.model.Modifiers.Modifier
import uml.model.lang.Lang.Language
import uml.model.types.Type

case class Argument(name: String, argumentType: Type, modifiers: List[Modifier], language: Language) {
  def write: String = s"$name: ${argumentType.name}"
}