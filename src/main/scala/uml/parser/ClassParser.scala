package uml.parser

import uml.model.annotations.Annotation

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

abstract class ClassParser extends RegexParsers {
  protected def annotations: Parser[Annotation] = {
    val annotationName = "\\w(\\w|_|\\d)*".r
    val propertyName = "\\w(\\w|\\d|_)*".r
    val propertyValue = "\\w(\\w|\\d|_|\"|[.])*".r

    val prop = (propertyName <~ "=") ~ propertyValue ^^ {
      case key ~ value => (key, value)
    }

    val value = propertyValue ^^ (v => ("value", v))

    val property = prop | value

    "@" ~> annotationName ~ ("(" ~> repsep(property, ",") <~ ")").? ^^ {
      case parsedName ~ properties =>
        Annotation(parsedName, properties.getOrElse(Nil).toMap)
    }
  }

  protected def anyChar: Regex = "(\n|\\w|\\d|;|,|[.]|\\[|\\]|\\?|\\s|:|<|>|%|&|[|]|\"|\'|!|[(]|[)]|=|[+]|-|/|[*])".r

  protected def block: Parser[List[String]] = "{" ~> block <~ "}" | anyChar.*

  protected def naming: Parser[String] = "\\w(\\d|_|\\w)*".r ^^ identity
}