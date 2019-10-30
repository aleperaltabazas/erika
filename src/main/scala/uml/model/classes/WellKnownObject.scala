package uml.model.classes

import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.lang.Lang.Language
import uml.model.methods.Method

case class WellKnownObject(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                           parent: Option[Class], interfaces: List[Class], language: Language)
  extends Class {
  override protected def definition: String = s"object $name"
}