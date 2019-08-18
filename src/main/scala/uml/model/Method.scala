package uml.model

import uml.model.Modifiers.Modifier

case class Method(name: String, returnType: String, arguments: List[Argument], modifiers: List[Modifier]) extends Modifiable