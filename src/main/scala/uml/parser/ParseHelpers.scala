package uml.parser

import uml.constants.Regex
import uml.exception.ParseError
import uml.model.Modifiers.Modifier
import uml.utils.Implicits.RichString

case object ParseHelpers {
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

  case class AccumulateAnnotationsUntil(untilRegex: String) {
    def apply(lines: List[String], discardWith: String): List[String] = lines.foldLeft((List[String](), List[String]())) {
      (accum, line) => {
        accum match {
          case (accumulated, annotations) =>
            if (line.matches(untilRegex)) {
              val joinedAnnotations = annotations.mkString("\n")
              (accumulated ++ List(joinedAnnotations.concat(s"\n$line")), Nil)
            } else if (line.matches(Regex.ANNOTATION)) {
              (accumulated, annotations ++ List(line))
            } else if (line.matches(discardWith)) {
              (accumulated, Nil)
            } else {
              accum
            }
        }
      }
    }._1
  }

  case object ParseTypeAndName {
    def apply[R <: RuntimeException](words: List[String], modifiers: List[Modifier], annotations: List[String])
                                    (implicit ex: String => R): (String, String) = {
      words.drop(modifiers.size + annotations.size) match {
        case _type :: name :: _ =>
          if (name.contains("(")) (name.take(name.indexOf("(")), _type)
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

  case object AnnotationTrim {
    def apply(str: String): String = {
      var inParenthesis = 0

      str.foldLeft("") { (acc: String, char) =>
        if (char == '(') inParenthesis = inParenthesis + 1
        if (char == ')') inParenthesis = inParenthesis - 1

        if (inParenthesis > 0 && char == ' ') acc
        else acc.appended(char)
      }
    }
  }

  case object UnwrapGeneric {
    def apply(str: String): String = {
      if (str.contains("<")) str.take(str.indexOf('<'))
      else str
    }
  }

}