package uml.model

import uml.model.Modifiers.Modifier
import uml.model.types.Type

case class Attribute(name: String, attributeType: Type, modifiers: List[Modifier], annotations: List[String])
  extends Modifiable