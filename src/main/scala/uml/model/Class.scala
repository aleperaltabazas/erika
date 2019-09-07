package uml.model

import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier
import uml.model.types.{GenericType, SimpleType}

case class Class(name: String, attributes: List[Attribute], methods: List[Method], modifiers: List[Modifier],
                 annotations: List[String], parent: Option[Class], interfaces: List[Class], classType: ClassType)
  extends Modifiable {

  def write: String = {
    val attributesText = attributes.filter(a => a.isVisible || hasGetterFor(a))
      .map(a => a.write)
      .mkString("\n")

    val methodsText = methods.filter(method => method.isVisible && !method.isBoilerplate(this))
      .map(m => m.write)
      .mkString("\n")

    val definition = s"${classType.write} $name"

    val inheritance = parent match {
      case Some(p) => s" extends ${p.name}"
      case None => ""
    }

    val implementation = interfaces match {
      case Nil => ""
      case x :: Nil => s" implements $x"
      case _ :: _ :: _ => s" implements ${interfaces.map(i => i.name).mkString(", ")}"
    }

    s"$definition$inheritance$implementation {\n$attributesText\n$methodsText\n}".trim
  }

  def writeRelations: String = {
    attributes
      .filter(attr => !attr.isStandard)
      .map {
        attr =>
          attr.attributeType match {
            case SimpleType(name) => s"${this.name} --> $name"
            case GenericType(_, composingTypes) => name + " --> \"*\" " + composingTypes.last.name
          }
      }.mkString("\n")
  }

  private def hasGetterFor(attribute: Attribute): Boolean = methods.exists(_.name == s"get${attribute.name.capitalize}")

}