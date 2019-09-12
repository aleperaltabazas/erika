package uml.model

import uml.model.Modifiers.Modifier
import uml.model.types.Type

case class Method(name: String, returnType: Type, arguments: List[Argument], modifiers: List[Modifier],
                  annotations: List[String]) extends Modifiable {
  def isBoilerplate(clazz: Class): Boolean = clazz.attributes
    .exists(attr => name == attr.getterMethod || name == attr.setterMethod)

  private def isGetterFor(clazz: ActualClass): Boolean = clazz.attributes.exists(attr => name == s"get${attr.name.capitalize}")

  def write: String = s"$name(${arguments.map(a => s"${a.write}").mkString(", ")}): ${returnType.name}"
}