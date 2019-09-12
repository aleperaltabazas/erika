package uml.model

import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier

case class Enum(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                annotations: List[String], interfaces: List[Class]) extends Class {
  override def parent: Option[ActualClass] = None

  override protected def definition: String = s"enum $name"
}