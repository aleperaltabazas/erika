package uml.model.classes

import uml.model.Modifiers
import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.methods.Method

case class ActualClass(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                       annotations: List[String], parent: Option[Class], interfaces: List[Class])
  extends Class {
  override protected def definition: String = if (isAbstract) s"abstract class $name" else s"class $name"

  def isAbstract = modifiers.contains(Modifiers.Abstract)
}
