package uml.model.classes

import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.methods.Method

case class Interface(name: String, methods: List[Method], modifiers: List[Modifier], annotations: List[String],
                     parent: Option[Class])
  extends Class {
  override protected def definition: String = s"interface $name"

  override def attributes: List[Attribute] = Nil

  override def interfaces: List[ActualClass] = Nil
}
