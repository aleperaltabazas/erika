package uml.builder

import uml.model.Modifiers.Modifier
import uml.model.{Argument, Method}

case class MethodBuilder(name: String, returnType: String, arguments: List[Argument], modifiers: List[Modifier],
                         annotations: List[String]) extends Builder {
  def build: Method = ???
}