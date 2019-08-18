package uml.model

import uml.model.Modifiers.Modifier

case class Attribute(name: String, attributeType: String, annotations: List[String],
                     modifiers: List[Modifier]) extends Modifiable {
}