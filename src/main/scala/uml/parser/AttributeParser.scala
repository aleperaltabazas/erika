package uml.parser

import uml.builder.AttributeBuilder
import uml.constants.Regex
import uml.exception.AttributeParseError
import uml.model.Attribute
import uml.model.Modifiers.Modifier
import uml.parser.ParseHelpers._
import uml.utils.Implicits._

case object AttributeParser {
  def parse(body: List[String]): List[Attribute] = {
    AccumulateAnnotationsUntil(Regex.ATTRIBUTE)(body, Regex.METHOD).map(parseIntoBuilder).map(_.build)
  }

  def parseIntoBuilder: String => AttributeBuilder = line => {
    val effectiveLine = removeInitialization(line)
    val words: List[String] = effectiveLine.words

    val modifiers: List[Modifier] = ParseModifiers(words)
    val annotations: List[String] = ParseAnnotations(words)
    val (name, attributeType): (String, String) = ParseTypeAndName(words, modifiers, annotations)(AttributeParseError)

    AttributeBuilder(name, attributeType, modifiers, annotations)
  }

  private def removeInitialization(line: String): String = {
    Option(line).filter(_.contains("=")).map(l => l.take(l.indexOf("="))).getOrElse(line)
  }

}