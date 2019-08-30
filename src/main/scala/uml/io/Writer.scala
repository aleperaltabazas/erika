package uml.io

import uml.model.Class

case object Writer {
  def apply(classes: List[Class]): String = {
    (classes.map(_.write) ++ classes.map(_.writeRelations)).mkString("\n")
  }
}