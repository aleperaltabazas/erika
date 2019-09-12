package uml.model

import uml.model.Modifiers.Modifier
import uml.model.types.{StandardTypes, Type}

case class Attribute(name: String, attributeType: Type, modifiers: List[Modifier], annotations: List[String])
  extends Modifiable {
  def getterMethod: String = s"get${name.capitalize}"

  def setterMethod: String = s"set${name.capitalize}"

  def isCollection: Boolean = attributeType.matchesWith("List") || attributeType.matchesWith("Map") ||
    attributeType.matchesWith("Set")

  def write: String = s"$name: ${attributeType.name}"

  def isStandard: Boolean = StandardTypes.contains(attributeType)

}