package uml.model.classes

import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.lang.Lang.Language
import uml.model.methods.Method

case class Enum(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                interfaces: List[Class], clauses: List[String], language: Language) extends Class {
  override def parent: Option[ActualClass] = None

  override protected def definition: String = s"enum $name"
}
