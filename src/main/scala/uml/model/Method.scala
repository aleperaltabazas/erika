package uml.model

import uml.model.Modifiers.Modifier

case class Method(name: String, returnType: Type, arguments: List[Argument], modifiers: List[Modifier],
                  annotations: List[String]) extends Modifiable