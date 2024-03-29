package uml.io

import uml.model.classes.Class
import uml.parser.java.JavaClassParser
import uml.utils.Functions.ReadFilesRecursively

case object Reader {
  def apply(basePath: String): List[Class] = {
    val classesText = ReadFilesRecursively(basePath)
    JavaClassParser.parseClasses(classesText)
  }
}