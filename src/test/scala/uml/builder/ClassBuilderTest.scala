package uml.builder

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import test.utils.Implicits._
import uml.model.ClassTypes.ConcreteClass
import uml.model.Modifiers.PackagePrivate
import uml.model.{Class, ClassTypes}
import uml.repository._

case class ClassBuilderTest() extends FlatSpec with Matchers with BeforeAndAfter {
  var classRepository: ClassRepository = new ClassRepository()
  var builderRepository: ClassBuilderRepository = new ClassBuilderRepository()

  before {
    classRepository = new ClassRepository()
    builderRepository = new ClassBuilderRepository()
  }

  "build" should "work with no parent nor interfaces" in {
    val builder: ClassBuilder = ClassBuilder("Foo", List(), List(), List(), List(), List(), ClassTypes.ConcreteClass, None)

    builderRepository.add(builder)
    classRepository shouldBeSize 0
    builder.build(classRepository, builderRepository)
    classRepository shouldBeSize 1
    builderRepository shouldBeSize 0

    val expectedClass: Class = Class("Foo", List(), List(), List(PackagePrivate), List(), None, List(), ClassTypes.ConcreteClass)

    classRepository shouldContain expectedClass
  }

  "build" should "work with parent and no interfaces" in {
    val parentBuilder: ClassBuilder = ClassBuilder("Bar", List(), List(), List(), List(), List(), ClassTypes
      .ConcreteClass, None)
    val builder: ClassBuilder = ClassBuilder("Foo", List(), List(), List(), List(), List(), ClassTypes.ConcreteClass,
      Some("Bar"))

    builderRepository.addAll(List(builder, parentBuilder))
    builder.build(classRepository, builderRepository)
    classRepository shouldBeSize 2
    builderRepository shouldBeSize 0

    val expectedSuper: Class = Class("Bar", List(), List(), List(PackagePrivate), List(), None, List(), ConcreteClass)
    val expectedChild: Class = Class("Foo", List(), List(), List(PackagePrivate), List(), Some(expectedSuper), List(),
      ConcreteClass)

    classRepository shouldContain expectedChild
    classRepository shouldContain expectedSuper
  }
}