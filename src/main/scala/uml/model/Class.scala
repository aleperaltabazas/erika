package uml.model

import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier

case class Class(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                 annotations: List[String], parent: Option[Class], interfaces: List[Class], classType: ClassType)
  extends Modifiable