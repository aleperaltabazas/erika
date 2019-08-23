package uml.builder

import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier
import uml.model.{Attribute, Class, Method}

case class ClassBuilder(name: String, attributes: List[Attribute] = List(),
                        methods: List[Method] = List(),
                        modifiers: List[Modifier] = List(),
                        annotations: List[String] = List(),
                        parent: Class,
                        interfaces: List[String] = List(),
                        classType: ClassType,
                        declaredSuper: Option[String] = None) {

}