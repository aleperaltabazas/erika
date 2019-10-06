package uml.parser

import uml.builder.AttributeBuilder
import uml.constants.Regex
import uml.exception.AttributeParseError
import uml.model.Modifiers
import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.types.{GenericType, Type}
import uml.parser.ParseHelpers._
import uml.utils.Implicits._

import scala.util.parsing.combinator._


case object AttributeParser extends RegexParsers {
  def parseAttributes(body: List[String]): List[Attribute] = {
    AccumulateAnnotationsUntil(Regex.ATTRIBUTE)(body, Regex.METHOD).map(parseIntoBuilder).map(_.build)
  }

  def parseAttribute(attribute: String): Attribute = {
    this.parse(defineParser, attribute) match {
      case Success(result, _) => result
      case Failure(msg, _) => throw AttributeParseError(msg)
      case Error(msg, _) => throw AttributeParseError(msg)
    }
  }

  private def defineParser: Parser[Attribute] = {
    def annotations: Parser[String] = "@\\w+([(].*[)])?".r ^^ identity

    def modifiers: Parser[Modifier] = "(public|protected|private|static|final|volatile|synchronized)".r ^^ {
      case "public" => Modifiers.Public
      case "private" => Modifiers.Private
      case "protected" => Modifiers.Protected
      case "static" => Modifiers.Static
      case "final" => Modifiers.Final
      case "synchronized" => Modifiers.Synchronized
      case "volatile" => Modifiers.Volatile
      case s: String => throw AttributeParseError(s"No such annotation $s")
    }

    def simpleType: Parser[Type] = "\\w+".r ^^ {
      Type(_)
    }

    def root: Parser[Type] = genericType | ("\\w+".r ^^ {
      Type(_)
    })

    def genericType: Parser[Type] = "\\w+".r ~ ("<" ~> repsep(root, ",") <~ ">") ^^ {
      result =>
        val genericWrapper = result._1
        val genericImplementation = result._2
        GenericType(genericWrapper, genericImplementation)
    }

    def typing = genericType | simpleType

    def naming: Parser[String] = "\\w+".r ^^ identity

    def expression = ";".r | ".*;".r

    annotations.* ~ modifiers.* ~ typing ~ naming <~ expression ^^ {
      result =>
        val name = result._2
        val _type = result._1._2
        val modifiers = result._1._1._2
        val annotations = result._1._1._1
        AttributeBuilder(name, _type, modifiers, annotations).build
    }
  }

  def parseIntoBuilder: String => AttributeBuilder = line => {
    val effectiveLine = removeInitialization(line)
    val words: List[String] = effectiveLine.words

    val modifiers: List[Modifier] = ParseModifiers(words)
    val annotations: List[String] = ParseAnnotations(words)
    val (name, attributeType): (String, String) = ParseTypeAndName(words, modifiers, annotations)(AttributeParseError)

    AttributeBuilder(name, Type of attributeType, modifiers, annotations)
  }

  private def removeInitialization(line: String): String = {
    Option(line).filter(_.contains("=")).map(l => l.take(l.indexOf("="))).getOrElse(line)
  }

}