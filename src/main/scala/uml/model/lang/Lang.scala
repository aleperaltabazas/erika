package uml.model.lang

import uml.model.Modifiers.Modifier
import uml.model.annotations.Annotation

case object Lang {

  sealed trait Language

  case class Java(annotations: List[Annotation]) extends Language

}