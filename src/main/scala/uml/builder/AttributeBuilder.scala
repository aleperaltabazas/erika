package uml.builder

import uml.model.Attribute
import uml.model.Modifiers.Modifier

case class AttributeBuilder(name: String, attributeType: String, modifiers: List[Modifier],
                            annotations: List[String]) extends Builder {

  def build: Attribute = {
    Attribute(name.replaceAll(";", ""), attributeType, effectiveModifiers, annotations)
  }

}