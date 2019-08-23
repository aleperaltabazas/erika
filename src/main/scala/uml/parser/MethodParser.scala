package uml.parser

import uml.builder.MethodBuilder
import uml.exception.{ArgumentParseError, MethodParseError}
import uml.model.Modifiers.Modifier
import uml.model.{Argument, Method, Type}
import uml.parser.LineParsingHelpers.{GenericReplacement, ParseAnnotations, ParseModifiers, ParseTypeAndName}

case object MethodParser {

  def parseArguments(args: List[String]): List[Argument] = {
    val dropParenthesis: String = args.mkString(" ").replaceAll("([(]|[)]|[{]|;) ?", "")
    val mappedString: List[String] = GenericReplacement(dropParenthesis).split(",").toList

    for {
      arg <- mappedString
    } yield {
      arg.split("\\s").toList match {
        case _ :+ _type :+ name => Argument(name, Type(_type))
        case _ => throw ArgumentParseError(s"Error parsing argument $arg")
      }
    }
  }

  def parseIntoBuilder(line: String): MethodBuilder = {
    val words: List[String] = line.split("\\n").toList

    val modifiers: List[Modifier] = ParseModifiers(words)
    val annotations: List[String] = ParseAnnotations(words)
    val (name, returnType): (String, String) = ParseTypeAndName(words, modifiers, annotations)(MethodParseError)
    val arguments = parseArguments(words.slice(words.indexOf(name), words.size - 1))

    MethodBuilder(name, returnType, arguments, modifiers, annotations)
  }

  def parse(body: List[String]): List[Method] = {
    for {
      line <- body
    } yield parseIntoBuilder(line).build
  }


}