package uml

import uml.io.{Reader, Writer}

object Main {
  def main(args: Array[String]): Unit = {
    val (basePath, fileName) = args.toList match {
      case Nil => throw new IllegalArgumentException("ERROR: need at least one argument, source path, to run.")
      case x :: Nil => (x, "classes.puml")
      case x :: y :: _ => (x, y)
    }

    val classFiles = Reader(basePath)
    val writtenClasses = Writer.classDiagram(classFiles)
  }
}