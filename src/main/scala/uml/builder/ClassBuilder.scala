package uml.builder

import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier
import uml.model.{Attribute, Class, Method}

case class ClassBuilder(name: String,
                        attributes: List[Attribute],
                        methods: List[Method],
                        modifiers: List[Modifier],
                        annotations: List[String],
                        interfaces: List[String],
                        classType: ClassType,
                        declaredSuper: Option[String]) extends Builder {
  def build: Class = ???
}