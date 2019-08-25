package uml.builder

import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier
import uml.model.{Attribute, Class, Method}

case class ClassBuilder(name: String,
                        attributes: List[Attribute],
                        methods: List[Method],
                        modifiers: List[Modifier],
                        annotations: List[String],
                        interfaces: List[String],
                        classType: ClassType,
                        declaredSuper: Option[String]) extends Builder {

  def buildWithDependencies(builtClasses: List[Class], builders: List[ClassBuilder]): (List[Class], List[ClassBuilder]) = {
    (declaredSuper, interfaces) match {
      case (None, Nil) => buildAlone(builders)
      case (Some(parent), Nil) => buildWithParent(builtClasses, builders, parent)
      case (None, is) => buildWithInterfaces(builtClasses, builders, is)
      case (Some(parent), is) => buildWithParentAndInterfaces(builtClasses, builders, parent, is)
    }
  }

  private def buildAlone(builders: List[ClassBuilder]): (List[Class], List[ClassBuilder]) = {
    val self = Class(name, attributes, methods, effectiveModifiers, annotations, None, Nil, classType)
    val remainingBuilders = builders.filterNot(_.name == name)

    (List(self), remainingBuilders)
  }

  private def buildWithParent(builtClasses: List[Class], builders: List[ClassBuilder], parent: String): (List[Class],
    List[ClassBuilder]) = ???

  private def buildWithInterfaces(builtClasses: List[Class], builders: List[ClassBuilder], interfaces: List[String]): (List[Class],
    List[ClassBuilder]) = ???

  private def buildWithParentAndInterfaces(builtClasses: List[Class], builders: List[ClassBuilder], parent: String,
                                           is: List[String]): (List[Class], List[ClassBuilder]) = ???
}