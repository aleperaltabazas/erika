package uml.model.attributes

import uml.model.Modifiers.Modifier
import uml.model.lang.Lang.Language
import uml.model.types.{StandardTypes, Type}
import uml.model.{Member, Modifiable}

case class Attribute(name: String, attributeType: Type, modifiers: List[Modifier], language: Language)
  extends Modifiable with Member {
  def getterMethod: String = s"get${name.capitalize}"

  def setterMethod: String = s"set${name.capitalize}"

  def isCollection: Boolean = attributeType.matchesWith("List") || attributeType.matchesWith("Map") ||
    attributeType.matchesWith("Set")

  def write: String = s"$name: ${attributeType.name}"

  def isStandard: Boolean = StandardTypes.contains(attributeType)

}
