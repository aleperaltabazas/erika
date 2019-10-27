package uml.model.methods

import uml.model.Modifiers.Modifier
import uml.model.classes.Class
import uml.model.types.Type
import uml.model.{Member, Modifiable}

case class Method(name: String, outputType: Type, arguments: List[Argument], modifiers: List[Modifier],
                  annotations: List[String]) extends Modifiable with Member {

  def isBoilerplate(clazz: Class): Boolean = clazz.attributes
    .exists(attr => name == attr.getterMethod || name == attr.setterMethod)

  def write: String = s"$name(${arguments.map(a => s"${a.write}").mkString(", ")}): ${outputType.name}"
}
