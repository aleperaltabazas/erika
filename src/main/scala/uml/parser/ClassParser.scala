package uml.parser

import uml.builder.ClassBuilder
import uml.constants.Regex

case object ClassParser {

  def isClassDefnition(str: String): Boolean = str.matches(Regex.CLASS_DEFINITION)

  def parseAnnotations(text: String): List[String] = {
    val lines: List[String] = text.split("\n").toList
    val effectiveLines = lines.takeWhile(!isClassDefnition(_))

    effectiveLines.filter(line => line.matches(Regex.ANNOTATION))
  }

  def parseIntoBuilder(text: String): ClassBuilder = {
    val usefulText: String = (filterImports andThen filterPackages) (text)
    val annotations: List[String] = parseAnnotations(text)
    ???
  }

  def filterImports: String => String = text => text.removeByRegex("import .*;")

  def filterPackages: String => String = text => text.removeByRegex("package .*;")

  implicit class RichString(string: String) {
    def removeByRegex(regex: String): String = string.replaceAll(regex, "")
  }

}