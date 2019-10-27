package uml.builder

import uml.model.Modifiers.Modifier
import uml.model.annotations.Annotation
import uml.model.methods.{Argument, Method}
import uml.model.types.Type

case class MethodBuilder(name: String, returnType: String, arguments: List[Argument], modifiers: List[Modifier],
                         annotations: List[Annotation]) extends Builder {
  def build: Method = Method(name, Type of returnType, arguments, effectiveModifiers, annotations)
}