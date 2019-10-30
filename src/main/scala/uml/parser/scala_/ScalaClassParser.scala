package uml.parser.scala_

import uml.builder.ClassBuilder
import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.classes.ClassTypes
import uml.model.classes.ClassTypes.ClassType
import uml.model.lang.Lang
import uml.model.methods.{Argument, Method}
import uml.model.types.{StandardTypes, Type}
import uml.parser.ClassParser
import uml.utils.Implicits._

case class ScalaClassParser() extends ClassParser {
  private val modifier: Parser[Modifier] = "lazy|final|override|case|abstract|implicit".r ^^ (_.toModifier(Lang.Scala))
  private val visibility: Parser[Modifier] = "private|protected".r ^^ (_.toModifier(Lang.Scala))
  private val simpleType: Parser[Type] = "\\w+".r ^^ (Type(_))
  private val typeBound = "<:|>:".r ~> "\\w+".r
  private val typeVariance = "[+]|-".r
  private val genericType: Parser[Type] = "[" ~> typeVariance ~> typing <~ typeBound <~ "]"
  private val expression = block | anyChar
  private val initialization = "=" ~> expression
  private val packageParser = "package" ~ "\\w|\\d|_|[.]".r
  private val importParser = "import" ~> "\\w|\\d|_|[.]|[{][}]".r

  private val valVar = "var|val"

  private def argumentParser: Parser[Argument] = naming ~ typing <~ initialization.? ^^ {
    case name ~ _type => Argument(name, _type, Nil, Lang.Scala(Nil))
  }

  private def typing: Parser[Type] = genericType | simpleType

  private def classParameterParser: Parser[Attribute] = (modifier | visibility).* ~ (valVar.? ~> naming) ~
    (typing <~ initialization.?) ^^ {
    case modifiers ~ name ~ _type => Attribute(name, _type, modifiers, Lang.Scala(Nil))
  }

  private def attributeParser: Parser[Attribute] = (modifier | visibility).* ~ (valVar ~> naming) ~ (typing.? <~
    initialization.?) ^^ {
    case modifiers ~ name ~ _type => Attribute(name, _type.getOrElse(StandardTypes.Any), modifiers, Lang.Scala(Nil))
  }

  private def methodParser: Parser[Method] = {
    val arguments = "(" ~> repsep(argumentParser, ",") <~ ")"

    val base = annotations.* ~ (modifier | visibility).* ~ ("def" ~> naming) ~ arguments.? ~ typing.?
    val pureMethod = base <~ initialization.? ^^ {
      case annotations ~ modifiers ~ name ~ args ~ _type => Method(name, _type.getOrElse(StandardTypes.Any),
        args.getOrElse(Nil), modifiers, Lang.Scala(annotations))
    }

    val unitMethod = base <~ block.? ^^ {
      case annotations ~ modifiers ~ name ~ args ~ _type => Method(name, _type.getOrElse(StandardTypes.Void), args
        .getOrElse(Nil), modifiers, Lang.Scala(annotations))
    }

    unitMethod | pureMethod
  }

  private def classTypeParser: Parser[ClassType] = "class|trait|object".r ^^ {
    case "class" => ClassTypes.ActualClass
    case "trait" => ClassTypes.Interface
    case "object" => ClassTypes.WellKnownObject
  }
}