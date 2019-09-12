package uml.model

import uml.model.Modifiers.Modifier
import uml.model.types.{GenericType, SimpleType}

trait Class extends Modifiable {

  def name: String

  def attributes: List[Attribute]

  def methods: List[Method]

  def modifiers: List[Modifier]

  def annotations: List[String]

  def parent: Option[Class]

  def interfaces: List[Class]

  private def isInherited(attribute: Attribute): Boolean = parent.exists(p => p.attributes.exists(_.name == attribute.name))

  private def isInherited(method: Method): Boolean = parent.exists(p => p.methods.exists(_.name == method.name))

  def write: String = {
    val attributesText = attributes.filter(a => (a.isVisible || hasGetterFor(a)) && !isInherited(a))
      .map(a => a.write)
      .mkString("\n")

    val methodsText = methods.filter(method => method.isVisible && !method.isBoilerplate(this) && !isInherited(method))
      .map(m => m.write)
      .mkString("\n")

    val definition = s"${this.definition}"

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

  protected def definition: String

  private def hasGetterFor(attribute: Attribute): Boolean = methods.exists(_.name == s"get${attribute.name.capitalize}")

}