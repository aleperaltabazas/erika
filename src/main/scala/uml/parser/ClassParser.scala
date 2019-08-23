package uml.parser

import uml.builder.ClassBuilder
import uml.constants.Regex
import uml.exception.NoClassDefinitionError
import uml.model.Attribute

case object ClassParser {

  def parseBody(className: String, text: String): List[String] = {
    val innerBody = text.substring(text.indexOf('{'), text.lastIndexOf('}') + 1)
      .split("\\n")
      .toList

    simplifyMethods.andThen(removeConstructor(className))(innerBody.slice(1, innerBody.size - 1))
  }

  def parseDefinition(lines: Array[String]): String = parseDefinition(lines.toList)

  def parseDefinition(lines: List[String]): String = lines.find(_.matches(Regex.CLASS_DEFINITION)) match {
    case Some(definition) => definition
    case None => throw NoClassDefinitionError(s"No class definition found. Error text: ${lines.mkString("\\n")}")
  }

  def parseName(definition: String): String = {
    val words = definition.split("\\s").toList
    words.find(_ == "class").orElse(words.find(_ == "enum")).orElse(words.find(_ == "interface"))
      .map(words.indexOf(_)) match {
      case Some(index) if words.size >= (index + 2) && words(index + 1).matches("[A-Z]\\w+") => words(index + 1)
      case _ => throw NoClassDefinitionError(s"No class definition could be parsed for definition $definition")
    }
  }

  def parseAnnotations(text: String): List[String] = text
    .split("(\n|\\s)")
    .takeWhile(!isClassDefnition(_))
    .filter(line => line.matches(Regex.ANNOTATION))
    .toList

  def parseIntoBuilder(text: String): ClassBuilder = {
    val effectiveText: String = (filterImports andThen filterPackages) (text)
    val lines = effectiveText.split("(\\s|\n)").toList
    val definition = parseDefinition(lines)
    val className = parseName(definition)
    val annotations: List[String] = parseAnnotations(text)
    val body: List[String] = parseBody(className, text)
    val attributes: List[Attribute] = AttributeParser.parse(body)

    ???
  }

  private def isClassDefnition(str: String): Boolean = str.matches(Regex.CLASS_DEFINITION)

  private def filterImports: String => String = text => text.removeByRegex("import .*;")

  private def filterPackages: String => String = text => text.removeByRegex("package .*;")

  private def simplifyMethods: List[String] => List[String] = lines => {
    var inExpressions = 0

    lines.foldLeft(List[String]()) {
      (acc, line) => {
        val accumulated = inExpressions match {
          case 0 => acc :+ line.removeByRegex("(;|[{]|[}])*").trim
          case _ => acc
        }

        if (line.contains("{")) inExpressions = inExpressions + 1
        if (line.contains("}")) inExpressions = inExpressions - 1

        accumulated
      }
    }
  }

  private def removeConstructor: String => List[String] => List[String] = { className =>
    lines =>
      for {
        line <- lines
        if line.matches(Regex.CONSTRUCTOR(className))
      } yield line
  }

  implicit class RichString(string: String) {
    def removeByRegex(regex: String): String = string.replaceAll(regex, "")
  }

}