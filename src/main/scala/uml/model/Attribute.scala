package uml.model

import uml.model.Modifiers.Modifier

case class Attribute(name: String, attributeType: String, modifiers: List[Modifier], annotations: List[String]) extends Modifiable