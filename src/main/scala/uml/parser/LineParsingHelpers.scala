package uml.parser

import uml.constants.Regex
import uml.exception.{NoSuchModifierException, ParseError}
import uml.model.Modifiers
import uml.model.Modifiers.Modifier

case object LineParsingHelpers {
  implicit private val parseError: String => ParseError = line => ParseError(s"Parse error in line: $line")

  case object ParseAnnotations {
    def apply(words: List[String]): List[String] = {
      words.filter(_.matches(s"${Regex.ANNOTATION}"))
    }
  }

  case object ParseModifiers {
    def apply(words: List[String]): List[Modifier] = {
      words.filter(_.matches(s"${Regex.VISIBILITY}|${
        Regex
          .MODIFIERS
      }")).map(_.toModifier)
    }
  }

  case object ParseTypeAndName {
    def apply[R <: RuntimeException](words: List[String], modifiers: List[Modifier], annotations: List[String])
                                    (implicit ex: String => R): (String, String) = {
      words.drop(modifiers.size + annotations.size) match {
        case _type :: name :: _ =>
          if (name.contains("(")) (name.substring(0, name.indexOf("(")), _type)
          else (name, _type)
        case _ => throw ex.apply(words.mkString(" "))
      }
    }
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
      case _ => throw NoSuchModifierException(str)
    }
  }

}