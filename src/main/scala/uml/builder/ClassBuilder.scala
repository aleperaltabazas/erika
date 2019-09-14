package uml.builder

import uml.exception.BuildError
import uml.model
import uml.model.Modifiers.Modifier
import uml.model.attributes.Attribute
import uml.model.classes.ClassTypes.ClassType
import uml.model.classes.{ActualClass, ClassTypes, Enum, Interface}
import uml.model.methods.Method
import uml.repository.{ClassBuilderRepository, ClassRepository}

case class ClassBuilder(name: String,
                        attributes: List[Attribute],
                        methods: List[Method],
                        modifiers: List[Modifier],
                        annotations: List[String],
                        interfaces: List[String],
                        classType: ClassType,
                        declaredSuper: Option[String],
                        enumClauses: List[String]) extends Builder {

  def build(classes: ClassRepository, builders: ClassBuilderRepository): Unit = {
    declaredSuper.flatMap(parent => builders.find(_.name == parent)).foreach(_.build(classes, builders))
    builders.findAll(builder => interfaces.contains(builder.name)).foreach(_.build(classes, builders))

    val builtSuper: Option[model.classes.Class] = declaredSuper.map(parent => classes.find(_.name == parent).getOrElse {
      throw BuildError(s"Error building class $name: parent $parent not built")
    })

    val builtInterfaces: List[model.classes.Class] = classes.findAll(c => interfaces.contains(c.name))

    if (builtInterfaces.length != interfaces.length) {
      throw BuildError(s"Error creating interfaces, expected $interfaces, built ${builtInterfaces.map(_.name)}")
    }

    val self = classType match {
      case ClassTypes.Enum => Enum(name, attributes, methods, effectiveModifiers, annotations, builtInterfaces, enumClauses)
      case ClassTypes.Interface => Interface(name, methods, effectiveModifiers, annotations, builtSuper)
      case ClassTypes.AbstractClass => ActualClass(name, attributes, methods, effectiveModifiers, annotations,
        builtSuper, builtInterfaces, isAbstract = true)
      case ClassTypes.ConcreteClass => ActualClass(name, attributes, methods, effectiveModifiers, annotations,
        builtSuper, builtInterfaces, isAbstract = false)
    }

    classes.add(self)
    builders.removeIf(_ == this)
  }
}