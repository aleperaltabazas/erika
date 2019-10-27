package uml.model.classes

import uml.model.Modifiers.{Default, Modifier}
import uml.model.annotations.Annotation
import uml.model.attributes.Attribute
import uml.model.methods.Method

case class Interface(name: String, methods: List[Method], modifiers: List[Modifier], annotations: List[Annotation],
                     parent: Option[Class])
  extends Class {
  override protected def definition: String = if (isTrait) s"interface $name << (T, #b6cae9) >>" else s"interface $name"

  override def attributes: List[Attribute] = Nil

  override def interfaces: List[ActualClass] = Nil

  private def isTrait: Boolean = methods.exists(m => m.is(Default))
}