package uml.builder

import uml.model.Argument
import uml.model.Modifiers.Modifier

case class MethodBuilder(name: String, returnType: String, arguments: List[Argument], modifiers: List[Modifier],
                         annotations: List[String])