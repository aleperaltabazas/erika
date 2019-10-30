package uml.parser.java

import uml.builder.ClassBuilder
import uml.model.Member
import uml.model.Modifiers.Generic
import uml.model.attributes.Attribute
import uml.model.classes.{Class, ClassTypes}
import uml.model.lang.Lang
import uml.model.lang.Lang.Java
import uml.model.methods.{Argument, Method}
import uml.model.types.Type
import uml.parser.ClassParser
import uml.repository.{ClassBuilderRepository, ClassRepository}
import uml.utils.Implicits._

case class JavaClassParser() extends ClassParser {
  private val visibility = "public|private|protected".r ^^ (_.toModifier(Java))
  private val modifiers = "abstract|static|final|volatile|synchronized|default".r ^^ (_.toModifier(Java))
  private val simpleTypeParser = "\\w+".r ^^ (Type(_))
  private val generic = "<" ~> repsep("\\w+".r, ",") <~ ">"
  private val genericTypeParser = "\\w+".r ~ generic ^^ {
    result => Type(s"${result._1}<${result._2.mkString(",")}>")
  }

  private val typing = genericTypeParser | simpleTypeParser

  private val assignation = "=" ~ (anyChar | block) ~ ";"

  private val genericModifier = generic ^^ (_ => Generic)

  private val argument = annotations.* ~ modifiers.* ~ typing ~ naming ^^ {
    case parsedAnnotations ~ parsedModifiers ~ parsedType ~ parsedName =>
      Argument(parsedName, parsedType, parsedModifiers, Lang.Java(parsedAnnotations))
  }

  private val methodBody = ("{" <~ block <~ "}") | ";"

  private val packageParser = "package" ~> "\\w+([.]|\\w|_|\\d|@)*".r ~> ";"
  private val importParser = "import" ~> modifiers.* ~> "\\w+([.]|\\w|_|\\d|[*])*".r ~> ";"

  private val classTypeParser = "class|interface|enum".r ^^ {
    case "class" => ClassTypes.ActualClass
    case "interface" => ClassTypes.Interface
    case "enum" => ClassTypes.Enum
  }

  private val genericClass = "<" ~> repsep("\\w+".r, ",") <~ ">" ^^ {
    result => s"<${result.mkString(", ")}>"
  }

  private val className = naming ~ genericClass.? ^^ {
    result => s"${result._1}${result._2.getOrElse("")}"
  }

  private val body = "{" ~> memberParser.* <~ "}"

  private val inheritance = "extends" ~> className
  private val implementation = "implements" ~> repsep(className, ",")

  private def classBuilderParser: Parser[ClassBuilder] = {
    packageParser.? ~> importParser.* ~> annotations.* ~ (visibility | modifiers).* ~ classTypeParser ~ className ~ inheritance.? ~
      implementation.? ~ body ^^ {
      case annotations ~ modifiers ~ classType ~ name ~ parent ~ interfaces ~ content =>
        val language = Lang.Java(annotations)
        var attributes: List[Attribute] = Nil
        var methods: List[Method] = Nil
        content.foldLeft(()) {
          (_, x) =>
            x match {
              case a: Attribute => attributes = attributes ++ List(a)
              case m: Method => methods = methods ++ List(m)
            }
        }
        ClassBuilder(name, attributes, methods, modifiers, interfaces.getOrElse(Nil), classType, parent, Nil, language)
    }
  }

  private def methodParser: Parser[Method] = {
    annotations.* ~ (modifiers | visibility | genericModifier).* ~ typing ~
      naming ~ ("(" ~> repsep(argument, ",") <~ ")") <~ methodBody ^^ {
      case parsedAnnotations ~ parsedModifiers ~ parsedType ~ parsedName ~ parsedArguments =>
        Method(parsedName, parsedType, parsedArguments, parsedModifiers, Lang.Java(parsedAnnotations))
    }
  }

  private def attributeParser: Parser[Attribute] = {
    annotations.* ~ (modifiers | visibility).* ~ typing ~ naming <~ (assignation | ";")
  } ^^ {
    case parsedAnnotations ~ parsedModifiers ~ parsedType ~ parsedName =>
      Attribute(parsedName, parsedType, parsedModifiers, Lang.Java(parsedAnnotations))
  }

  private def memberParser: Parser[Member] = methodParser | attributeParser | classBuilderParser

  def parseClassToBuilder(clazz: String): ClassBuilder = this.parse(classBuilderParser, clazz) match {
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