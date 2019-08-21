package uml.parser

import uml.builder.AttributeBuilder
import uml.constants.Regex
import uml.exception.{AttributeParseError, NoSuchModifierException}
import uml.model.Modifiers.Modifier
import uml.model.{Attribute, Modifiers}

case object AttributeParser {
  def parse(body: List[String]): List[Attribute] = {
    body.filter(_.matches(Regex.ATTRIBUTE))
      .map(line => parseIntoBuilder(line))
      .map(_.build)
  }

  def parseIntoBuilder(line: String): AttributeBuilder = {
    val effectiveLine = removeInitialization(line)
    val words: List[String] = effectiveLine.split("\\s").toList

    val modifiers: List[String] = words.filter(_.matches(s"${Regex.VISIBILITY}|${Regex.MODIFIERS}"))
    val effectiveModifiers = modifiers.map(_.toModifier)

    val annotations: List[String] = words.filter(_.matches(s"${Regex.ANNOTATION}"))

    val (attributeType, name) = words.drop(modifiers.size + annotations.size) match {
      case x :: y :: _ => (x, y)
      case _ => throw AttributeParseError(s"No name or type found within line $line")
    }

    AttributeBuilder(name, attributeType, effectiveModifiers, annotations)
  }

  private def removeInitialization(line: String): String = {
    Option(line).filter(_.contains("=")).map(l => l.substring(0, l.indexOf("="))).getOrElse(line)
  }

  implicit class RichString(str: String) {
    def toModifier: Modifier = str match {
      case "public" => Modifiers.Public
      case "private" => Modifiers.Private
      case "protected" => Modifiers.Protected
      case "static" => Modifiers.Static
      case "final" => Modifiers.Final
      case "default" => Modifiers.Default
      case "synchronized" => Modifiers.Synchronized
      case "volatile" => Modifiers.Volatile
      case _ => throw new NoSuchModifierException(str)
    }
  }

}