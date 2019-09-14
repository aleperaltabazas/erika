package uml.parser

import uml.builder.ClassBuilder
import uml.constants.Regex
import uml.exception.{IllegalExtensionError, NoClassDefinitionError, NoSuchTypeException}
import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.classes.ClassTypes.ClassType
import uml.model.classes.{Class, ClassTypes}
import uml.model.methods.Method
import uml.parser.ParseHelpers.{AnnotationTrim, GenericReplacement}
import uml.parser.Parsers.CharParser
import uml.repository.{ClassBuilderRepository, ClassRepository}
import uml.utils.Implicits.RichString

case object ClassParser {

  def parse(classes: List[String]): List[Class] = {
    val builders: List[ClassBuilder] = classes.map(clazz => parseIntoBuilder(clazz))
    val classRepository: ClassRepository = new ClassRepository
    val builderRepository: ClassBuilderRepository = new ClassBuilderRepository(builders)

    while (!builderRepository.isEmpty) {
      val builder = builderRepository.head
      builder.build(classRepository, builderRepository)
    }

    classRepository.getAll
  }

  def parseEnumClauses(body: List[String]): List[String] = simplifyMethods(body)
    .flatMap(line => line.split(",|;").toList)
    .map(_.trim)
    .filter(line => !line.isEmpty)
    .takeWhile(line => {
      line.matches(s"${Regex.ENUM_CONSTANT} ?")
    })
    .map(line => line.takeWhile(char => char != '(' && char != '{'
      && char != ',' && char != ';').removeByRegex("}|;|,|").trim)

  def parseIntoBuilder(text: String): ClassBuilder = {
    val effectiveText: String = (filterImports andThen filterPackages) (text)
    val lines: List[String] = effectiveText.splitBy("\n|\t")
    val definition: String = parseDefinition(lines)
    val definitionWords: List[String] = effectiveWords(definition)

    val className: String = parseName(definition, definitionWords)
    val classType: ClassType = parseType(className, definitionWords)
    val annotations: List[String] = parseAnnotations(text).map(_.trim)
    val body: List[String] = parseBody(className, text)
    val enumClauses = classType match {
      case ClassTypes.Enum => parseEnumClauses(body)
      case _ => Nil
    }
    val attributes: List[Attribute] = AttributeParser.parse(body)
    val methods: List[Method] = MethodParser.parse(body)
    val parent: Option[String] = parseSuper(className, definitionWords)
    val modifiers: List[Modifier] = parseModifiers(classType, definitionWords)
    val interfaces: List[String] = parseInterfaces(className, definitionWords)

    ClassBuilder(className, attributes, methods, modifiers, annotations, interfaces, classType, parent, enumClauses)
  }

  def parseBody(className: String, text: String): List[String] = {
    val innerBody = ('{' << text >> '}').splitBy("\\n").filter(line => !line.isEmpty)

    (simplifyMethods andThen filterConstructor(className)) (innerBody)
      .map(line => line.removeByRegex(";").trim)
  }

  private def simplifyMethods: List[String] => List[String] = lines => {
    lines.mkString("\n").replaceAll("[{]\n", "{")
      .replaceAll("\n[}]", "}")
      .splitBy("\n")
      .map(line => ('{' >> line << '}').replaceAll("[{][}]", ";")
        .removeByRegex("([{]|[}])*").trim)
  }

  private def filterConstructor: String => List[String] => List[String] = className => lines =>
    lines.filter(!_.matches(Regex.CONSTRUCTOR(className)))

  def parseAnnotations(text: String): List[String] = for {
    annotatedLine: String <- text.splitBy("\n").takeWhile(!isClassDefinition(_))
      .map(AnnotationTrim(_))
      .flatMap(_.splitBy("\\s"))
    if annotatedLine.matches(Regex.ANNOTATION)
  } yield annotatedLine

  private def isClassDefinition(str: String): Boolean = {
    val regex = Regex.CLASS_DEFINITION
    str.matches(regex)
  }

  def parseSuper(className: String, words: List[String]): Option[String] = {
    if (words.contains("extends")) {
      val parent = words(words.indexOf("extends") + 1)

      if (parent.trim == className) {
        throw IllegalExtensionError(className, parent, words.mkString(" "))
      }

      Some(parent)
    }

    else None
  }

  def parseType(className: String, words: List[String]): ClassType = {
    words(words.indexOf(className) - 1) match {
      case "class" if words.contains("abstract") => ClassTypes.AbstractClass
      case "class" => ClassTypes.ConcreteClass
      case "interface" => ClassTypes.Interface
      case "enum" => ClassTypes.Enum
      case _ => throw NoSuchTypeException(words.mkString(" "))
    }
  }

  def parseModifiers(classType: ClassType, words: List[String]): List[Modifier] = {
    words.take(words.indexOf(classType.toString))
      .map(str => str.toModifier)
  }

  def parseInterfaces(className: String, words: List[String]): List[String] = {
    if (words.contains("implements")) {
      words.drop(words.indexOf("implements") + 1)
        .map(GenericReplacement(_))
        .map(w => w.removeByRegex(","))
    }

    else List()
  }

  private def filterImports: String => String = text => text.removeByRegex("import .*;")

  private def filterPackages: String => String = text => text.removeByRegex("package .*;")

  def parseName(definition: String, words: List[String]): String = {
    val name = words.find(_ == "class").orElse(words.find(_ == "enum")).orElse(words.find(_ == "interface"))
      .map(words.indexOf(_)) match {
      case Some(index) if words.size >= (index + 2) && words(index + 1).matches(s"[A-Z]\\w+(${Regex.GENERIC})?") =>
        words(index + 1)
      case _ => throw NoClassDefinitionError(s"No class definition could be parsed for definition $definition")
    }

    if (name.contains("<")) name.take(name.indexOf('<'))
    else name
  }

  private def effectiveWords(definition: String): List[String] = {
    definition.removeByRegex(";|[{]|[}]").splitBy("\\s")
  }

  def parseDefinition(lines: Array[String]): String = parseDefinition(lines.toList)

  def parseDefinition(lines: List[String]): String = lines.find(_.matches(Regex.CLASS_DEFINITION)) match {
    case Some(definition) => definition.removeByRegex("[{]|[}]|;").trim
    case None => throw NoClassDefinitionError(s"No class definition found. Error text: ${lines.mkString("\\n")}")
  }

  def parseName(definition: String): String = parseName(definition, effectiveWords(definition))

}