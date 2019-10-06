package uml.builder

import uml.model.Modifiers.Modifier
import uml.model.attributes
import uml.model.attributes.Attribute
import uml.model.types.Type
import uml.utils.Implicits.RichString

case class AttributeBuilder(name: String, attributeType: Type, modifiers: List[Modifier],
                            annotations: List[String] = Nil) extends Builder {

  def build: Attribute = attributes.Attribute(name.removeByRegex(";"), attributeType, effectiveModifiers, annotations)

}