package uml.builder

import uml.model.Attribute
import uml.model.Modifiers.Modifier
import uml.model.types.Type
import uml.utils.Implicits.RichString

case class AttributeBuilder(name: String, attributeType: String, modifiers: List[Modifier],
                            annotations: List[String]) extends Builder {

  def build: Attribute = Attribute(name.removeByRegex(";"), Type of attributeType, effectiveModifiers, annotations)

}