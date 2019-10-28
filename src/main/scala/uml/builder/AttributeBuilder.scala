package uml.builder

import uml.model.Modifiers.Modifier
import uml.model.annotations.Annotation
import uml.model.attributes.Attribute
import uml.model.types.Type
import uml.utils.Implicits.RichString

case class AttributeBuilder(name: String, attributeType: Type, modifiers: List[Modifier],
                            annotations: List[Annotation] = Nil) extends Builder {

  def build: Attribute = Attribute(name.removeByRegex(";"), attributeType, effectiveModifiers, annotations)

}