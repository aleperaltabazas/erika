package uml.builder

import uml.model.Modifiers.Modifier
import uml.model.{Attribute, Modifiers}

case class AttributeBuilder(name: String, attributeType: String, modifiers: List[Modifier], annotations: List[String]) {

  def build: Attribute = {
    val effectiveModifiers: List[Modifier] = if (declaresVisibility) modifiers
    else modifiers :+ Modifiers.PackagePrivate

    Attribute(name.replaceAll(";", ""), attributeType, effectiveModifiers, annotations)
  }

  private def declaresVisibility: Boolean = {
    modifiers.contains(Modifiers.Public) || modifiers.contains(Modifiers.Protected) || modifiers.contains(Modifiers.Private)
  }

}