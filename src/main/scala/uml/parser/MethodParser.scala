package uml.parser

import uml.builder.MethodBuilder
import uml.exception.{ArgumentParseError, MethodParseError}
import uml.model.Modifiers.Modifier
import uml.model.{Argument, Method, Type}
import uml.parser.LineParsingHelpers.{ParseAnnotations, ParseModifiers, ParseTypeAndName}

case object MethodParser {

  def parseArguments(args: List[String]): List[Argument] = {
    val dropParenthesis: String = args.mkString(" ").replaceAll("([(]|[)]|[{]|;) ?", "")
    val mappedString: List[String] = MapStringToDomain(dropParenthesis).split(",").toList

    for {
      arg <- mappedString
    } yield {
      arg.split("\\s").toList match {
        case _ :+ _type :+ name => {
          Argument(name, Type(_type))
        }
        case _ => throw ArgumentParseError(s"Error parsing argument $arg")
      }
    }
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

  private case object MapStringToDomain {
    def apply(str: String): String = {
      var inGenerics = 0

      str.foldLeft("") { (acc: String, char) =>
        if (char == '<') inGenerics = inGenerics + 1
        if (char == '>') inGenerics = inGenerics - 1

        if (inGenerics > 0 && char == ' ') acc
        else if (inGenerics > 0 && char == ',') acc.appended('|')
        else acc.appended(char)
      }
    }
  }

}