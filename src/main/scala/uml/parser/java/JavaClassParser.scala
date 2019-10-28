package uml.parser.java

import uml.builder.ClassBuilder
import uml.model.Member
import uml.model.Modifiers.{Generic, Modifier}
import uml.model.annotations.Annotation
import uml.model.attributes.Attribute
import uml.model.classes.ClassTypes.ClassType
import uml.model.classes.{Class, ClassTypes}
import uml.model.methods.{Argument, Method}
import uml.model.types.Type
import uml.repository.{ClassBuilderRepository, ClassRepository}
import uml.utils.Implicits._

import scala.util.parsing.combinator.RegexParsers

case object JavaClassParser extends RegexParsers {
  private val annotations = "@" ~> "\\w+(\\w|_|\\d)?".r ~ ("(" ~> repsep("(\\w|[.]|\\d|\\s|=)*".r, ",") <~ ")").? ^^ {
    result =>
      val name = result._1
      val properties = result._2.map(props => props.map(_.splitBy("=") match {
        case x :: Nil => ("value", x.trim)
        case x :: y :: Nil => (x.trim, y.trim)
      })).getOrElse(Nil).toMap
      Annotation(name, properties)
  }
  private val visibility = "public|private|protected".r ^^ (_.toModifier)
  private val modifiers = "abstract|static|final|volatile|synchronized|default".r ^^ (_.toModifier)
  private val simpleTypeParser = "\\w+".r ^^ (Type(_))
  private val generic = "<" ~> repsep("\\w+".r, ",") <~ ">"
  private val genericTypeParser = "\\w+".r ~ generic ^^ {
    result => Type(s"${result._1}<${result._2.mkString(",")}>")
  }

  private val typing: Parser[Type] = genericTypeParser | simpleTypeParser
  private val naming: Parser[String] = "\\w+".r ^^ identity

  private val anyChar = "(\n|\\w|\\d|;|,|[.]|\\[|\\]|\\?|\\s|:|<|>|%|&|[|]|\"|\'|!|[(]|[)]|=|[+]|-|/|[*])".r

  private val expression: Parser[List[String]] = "{" ~> expression <~ "}" | anyChar.*

  private val assignation = "=" ~ anyChar ~ ";"
  private val attributeParser: Parser[Attribute] = {
    annotations.* ~ (modifiers | visibility).* ~ typing ~ naming <~ (assignation | ";")
  } ^^ {
    result =>
      val name = result._2
      val _type = result._1._2
      val allModifiers = result._1._1._2
      val allAnnotations = result._1._1._1

      Attribute(name, _type, allModifiers, allAnnotations)
  }

  private val genericModifier: Parser[Modifier] = generic ^^ (_ => Generic)

  private val argument = annotations.* ~ modifiers.* ~ typing ~ naming ^^ {
    result => Argument(result._2, result._1._2)
  }

  private val methodBody = ("{" <~ expression <~ "}") | ";"

  private val packageParser = "package" ~> "\\w+([.]|\\w|_|\\d|@)*".r ~> ";"
  private val importParser = "import" ~> modifiers.* ~> "\\w+([.]|\\w|_|\\d|[*])*".r ~> ";"

  private val methodParser: Parser[Method] = {
    annotations.* ~ (modifiers | visibility | genericModifier).* ~ typing ~
      naming ~ ("(" ~> repsep(argument, ",") <~ ")") <~ methodBody ^^ {
      result =>
        val parsedAnnotations ~ parsedModifiers ~ parsedType ~ parsedName ~ parsedArguments = result
        Method(parsedName, parsedType, parsedArguments, parsedModifiers, parsedAnnotations)
    }
  }

  private val memberParser: Parser[Member] = methodParser | attributeParser

  private val classTypeParser: Parser[ClassType] = "class|interface|enum".r ^^ {
    case "class" => ClassTypes.ActualClass
    case "interface" => ClassTypes.Interface
    case "enum" => ClassTypes.Enum
  }

  private val genericClass: Parser[String] = "<" ~> repsep("\\w+".r, ",") <~ ">" ^^ {
    result => s"<${result.mkString(", ")}>"
  }

  private val className: Parser[String] = "\\w(_|\\d|\\w)*".r ~ genericClass.? ^^ {
    result => s"${result._1}${result._2.getOrElse("")}"
  }

  private val body: Parser[List[Member]] = "{" ~> memberParser.* <~ "}"

  private val inheritance = "extends" ~> className
  private val implementation = "implements" ~> repsep(className, ",")
  private val classParser =
    packageParser.? ~> importParser.* ~> annotations.* ~ (visibility | modifiers).* ~ classTypeParser ~ className ~ inheritance.? ~
      implementation.? ~ body ^^ {
      case annotations ~ modifiers ~ classType ~ name ~ parent ~ interfaces ~ content =>
        var attributes: List[Attribute] = Nil
        var methods: List[Method] = Nil
        content.foldLeft(()) {
          (_, x) =>
            x match {
              case a: Attribute => attributes = attributes ++ List(a)
              case m: Method => methods = methods ++ List(m)
            }
        }
        ClassBuilder(name, attributes, methods, modifiers, annotations, interfaces.getOrElse(Nil), classType, parent, Nil)
    }

  def parseClassToBuilder(clazz: String): ClassBuilder = this.parse(classParser, clazz) match {
    case Success(result, _) => result
    case Failure(msg, next) => throw new RuntimeException(s"Parse failure: msg: $msg, next: $next")
    case Error(msg, next) => throw new RuntimeException(s"Parse error: msg $msg, next: $next")
  }

  def parseClasses(classes: List[String]): List[Class] = {
    val builders: List[ClassBuilder] = classes.map(clazz => parseClassToBuilder(clazz))
    val classRepository: ClassRepository = new ClassRepository
    val builderRepository: ClassBuilderRepository = new ClassBuilderRepository(builders)

    while (!builderRepository.isEmpty) {
      val builder = builderRepository.head
      builder.build(classRepository, builderRepository)
    }

    classRepository.getAll
  }
}
