package uml.utils

import java.io.File

import scala.io.Source

object Functions {

  case object ReadFilesRecursively {
    def apply(filePath: String): List[String] = {
      val file = new File(filePath)

      if (file.isDirectory) {
        file.listFiles.toList
          .filter(f => f.isDirectory || f.getName.endsWith(".java"))
          .flatMap(file => ReadFilesRecursively(file.getAbsolutePath))
      } else {
        val lines = Source.fromFile(file, "ISO-8859-1").getLines.mkString("\n")
        List(lines)
      }
    }
  }

}