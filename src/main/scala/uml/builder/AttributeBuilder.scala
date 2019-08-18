package uml.builder

import uml.model.Modifiers.Modifier

case class AttributeBuilder(name: String, attributeType: String, modifiers: List[Modifier], annotations: List[String])