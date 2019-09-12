package uml.model

import uml.model.Modifiers.Modifier

case class ActualClass(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                       annotations: List[String], parent: Option[Class], interfaces: List[Class],
                       isAbstract: Boolean)
  extends Class {
  override protected def definition: String = if (isAbstract) s"abstract class $name" else s"class $name"
}