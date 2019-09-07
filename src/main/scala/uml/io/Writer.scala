package uml.io

import java.io.{ByteArrayOutputStream, FileOutputStream}

import net.sourceforge.plantuml.{FileFormat, FileFormatOption, SourceStringReader}
import uml.model.Class

case object Writer {
  def classDiagram(classes: List[Class]): String = {
    "@startuml\n" +
      (classes.map(_.write) ++ classes.map(_.writeRelations)).mkString("\n") +
      "@enduml"
  }

  def apply(text: String)(path: String): Unit = {
    val stringReader = new SourceStringReader(text)
    val outputStream = new ByteArrayOutputStream()

    stringReader.generateImage(outputStream, new FileFormatOption(FileFormat.PNG))
    outputStream.close()

    try {
      val fileStream = new FileOutputStream(s"$path/classes.png")
      outputStream.writeTo(fileStream)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}