package uml.parser

import uml.builder.MethodBuilder
import uml.exception.MethodParseError
import uml.model.Modifiers.Modifier
import uml.model.{Argument, Method}
import uml.parser.LineParsingHelpers.{ParseAnnotations, ParseModifiers, ParseTypeAndName}

case object MethodParser {

  def parseWithGenerics(line: String): List[Argument] = ???

  def parseWithoutGenerics(line: String): List[Argument] = {
    line.split(",")
      .map { arg =>
        val words = arg.split("\\s")
        val name = words(words.size - 1)
        val _type = words(words.size - 2)

        Argument(name, _type)
      }
      .toList
  }

  def parseArguments(args: List[String]): List[Argument] = {
    val dropParenthesis: String = args.mkString(" ").replaceAll("([(]|[)]|[{]|;) ?", "")
    if (dropParenthesis.exists(c => c == '<' || c == '>')) parseWithGenerics(dropParenthesis)
    else parseWithoutGenerics(dropParenthesis)
  }

  def parseIntoBuilder(line: String): MethodBuilder = {
    val words: List[String] = line.split("\\n").toList

    val modifiers: List[Modifier] = ParseModifiers(words)
    val annotations: List[String] = ParseAnnotations(words)
    val (name, attributeType): (String, String) = ParseTypeAndName(words, modifiers, annotations)(MethodParseError)
    val arguments = parseArguments(words.slice(words.indexOf(name), words.size - 1))

    ???
  }

  def parse(body: List[String]): List[Method] = {
    for {
      line <- body
    } yield parseIntoBuilder(line).build
  }
}