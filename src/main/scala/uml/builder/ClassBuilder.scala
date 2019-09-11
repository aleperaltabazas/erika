package uml.builder

import uml.exception.BuildError
import uml.model.ClassTypes.ClassType
import uml.model.Modifiers.Modifier
import uml.model.{Attribute, Class, Method}
import uml.repository.{ClassBuilderRepository, ClassRepository}

case class ClassBuilder(name: String,
                        attributes: List[Attribute],
                        methods: List[Method],
                        modifiers: List[Modifier],
                        annotations: List[String],
                        interfaces: List[String],
                        classType: ClassType,
                        declaredSuper: Option[String]) extends Builder {

  def build(classes: ClassRepository, builders: ClassBuilderRepository): Unit = {
    declaredSuper.flatMap(parent => builders.find(_.name == parent)).foreach(_.build(classes, builders))
    builders.findAll(builder => interfaces.contains(builder.name)).foreach(_.build(classes, builders))

    val builtSuper: Option[Class] = declaredSuper.map(parent => classes.find(_.name == parent).getOrElse {
      throw BuildError(s"Error building class $name: parent $parent not built")
    })

    val builtInterfaces: List[Class] = classes.findAll(c => interfaces.contains(c.name))

    if (builtInterfaces.length != interfaces.length) {
      throw BuildError(s"Error creating interfaces, expected $interfaces, built ${builtInterfaces.map(_.name)}")
    }

    classes.add(Class(name, attributes, methods, effectiveModifiers, annotations, builtSuper, builtInterfaces, classType))
    builders.removeIf(_ == this)
  }
}