package uml.parser

import uml.builder.MethodBuilder
import uml.constants.Regex
import uml.exception.{ArgumentParseError, MethodParseError}
import uml.model.Modifiers.Modifier
import uml.model.types.Type
import uml.model.{Argument, Method}
import uml.parser.ParseHelpers.{AccumulateAnnotationsUntil, GenericReplacement, ParseAnnotations, ParseModifiers, ParseTypeAndName}
import uml.utils.Implicits.RichString

case object MethodParser {

  def parse(body: List[String]): List[Method] = {
    AccumulateAnnotationsUntil(Regex.METHOD)(body, Regex.ATTRIBUTE).map(parseIntoBuilder).map(_.build)
  }

  def parseIntoBuilder(line: String): MethodBuilder = {
    val words: List[String] = line.words

    val modifiers: List[Modifier] = ParseModifiers(words)
    val annotations: List[String] = ParseAnnotations(words)
    val (name, returnType): (String, String) = ParseTypeAndName(words, modifiers, annotations)(MethodParseError)
    val arguments = {
      val args = line.slice(line.indexOf("(") + 1, line.lastIndexOf(")"))

      parseArguments(args)
    }

    MethodBuilder(name, returnType, arguments, modifiers, annotations)
  }

  def parseArguments(args: String): List[Argument] = {
    args match {
      case "" | "()" => Nil
      case _ =>
        val dropParenthesis: String = args.removeByRegex("([(]|[)]|[{]|;) ?")
        val mappedString: List[String] = GenericReplacement(dropParenthesis).split(",").toList

        for {
          arg <- mappedString
        } yield {
          arg.split("\\s").toList match {
            case _ :+ _type :+ name if !name.isEmpty && !_type.isEmpty => Argument(name, Type(_type))
            case _ => throw ArgumentParseError(s"Error parsing argument $arg")
          }
        }
    }
  }

}