package uml.model.lang

import uml.model.annotations.Annotation

case object Lang {

  sealed trait Language

  case class Java(annotations: List[Annotation]) extends Language
  case object Java extends Language

  case class Scala(annotations: List[Annotation]) extends Language
  case object Scala extends Language
}