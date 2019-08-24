package uml.model

import uml.model.Modifiers.Modifier
import uml.model.types.Type

case class Method(name: String, returnType: Type, arguments: List[Argument], modifiers: List[Modifier],
                  annotations: List[String]) extends Modifiable