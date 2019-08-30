package uml.main

import uml.model.Class
import uml.parser.ClassParser
import uml.utils.Functions.ReadFilesRecursively

object Main {
  def main(args: Array[String]): Unit = {
    val (basePath, fileName) = args.toList match {
      case Nil => throw new IllegalArgumentException("ERROR: need at least one argument, source path, to run.")
      case x :: Nil => (x, "classes.puml")
      case x :: y :: _ => (x, y)
    }

    val classesText = ReadFilesRecursively(basePath)
    val classes: List[Class] = ClassParser.parse(classesText)
  }
}