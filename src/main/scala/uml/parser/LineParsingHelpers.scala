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
      for {
        word <- words
        if word.matches(s"${Regex.VISIBILITY}|${Regex.MODIFIERS}")
      } yield word.toModifier
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

  case object GenericReplacement {
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

  private implicit class RichString(str: String) {
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