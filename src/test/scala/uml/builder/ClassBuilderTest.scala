package uml.builder

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import test.utils.Implicits._
import uml.model.Modifiers.PackagePrivate
import uml.model.classes.{ActualClass, ClassTypes, Interface}
import uml.model.{Modifiers, classes}
import uml.repository._

case class ClassBuilderTest() extends FlatSpec with Matchers with BeforeAndAfter {
  val classBuilder: ClassBuilder = ClassBuilder(
    name = "",
    attributes = Nil,
    methods = Nil,
    modifiers = Nil,
    annotations = Nil,
    interfaces = Nil,
    classType = ClassTypes.ActualClass,
    declaredSuper = None,
    enumClauses = Nil
  )
  val interfaceBuilder: ClassBuilder = ClassBuilder(
    name = "",
    attributes = Nil,
    methods = Nil,
    modifiers = Nil,
    annotations = Nil,
    interfaces = Nil,
    classType = ClassTypes.Interface,
    declaredSuper = None,
    enumClauses = Nil
  )
  val abstractClassBuilder: ClassBuilder = ClassBuilder(
    name = "",
    attributes = Nil,
    methods = Nil,
    modifiers = List(Modifiers.Abstract),
    annotations = Nil,
    interfaces = Nil,
    classType = ClassTypes.ActualClass,
    declaredSuper = None,
    enumClauses = Nil
  )

  val clazz: ActualClass = ActualClass("", Nil, Nil, List(PackagePrivate), Nil, None, Nil)
  val interface: Interface = Interface("", Nil, List(PackagePrivate), Nil, None)
  val abstractClass: ActualClass = ActualClass("", Nil, Nil, List(Modifiers.Abstract, PackagePrivate), Nil, None, Nil)
  var classRepository: ClassRepository = new ClassRepository()
  var builderRepository: ClassBuilderRepository = new ClassBuilderRepository()

  before {
    classRepository = new ClassRepository()
    builderRepository = new ClassBuilderRepository()
  }

  "build" should "work with no parent nor interfaces" in {
    val builder: ClassBuilder = classBuilder.copy(name = "Foo")

    builderRepository.add(builder)
    classRepository shouldBeSize 0
    builder.build(classRepository, builderRepository)
    classRepository shouldBeSize 1
    builderRepository shouldBeSize 0

    val expectedClass: ActualClass = clazz.copy(name = "Foo")

    classRepository.elements(0) shouldBe expectedClass
  }

  "build" should "work with parent and no interfaces" in {
    val parentBuilder: ClassBuilder = classBuilder.copy(name = "Bar")
    val builder: ClassBuilder = classBuilder.copy(name = "Foo", declaredSuper = Some("Bar"))

    builderRepository.addAll(List(builder, parentBuilder))
    builder.build(classRepository, builderRepository)
    classRepository shouldBeSize 2
    builderRepository shouldBeSize 0

    val expectedSuper: ActualClass = clazz.copy(name = "Bar")
    val expectedChild: ActualClass = clazz.copy(name = "Foo", parent = Some(expectedSuper))

    classRepository shouldContain expectedChild
    classRepository shouldContain expectedSuper
  }

  "build" should "work with interfaces" in {
    val biz: ClassBuilder = interfaceBuilder.copy(name = "Biz")
    val baz: Interface = interface.copy(name = "Baz")

    val foo: ClassBuilder = classBuilder.copy(name = "Foo", interfaces = List("Biz", "Baz"))

    builderRepository.addAll(List(biz, foo))
    classRepository.add(baz)

    foo.build(classRepository, builderRepository)

    classRepository shouldBeSize 3
    builderRepository shouldBeSize 0

    val expectedBiz: Interface = interface.copy(name = "Biz")
    val expectedFoo: classes.Class = clazz.copy(name = "Foo", interfaces = List[classes.Class](expectedBiz, baz))

    classRepository shouldContain expectedFoo
    classRepository shouldContain expectedBiz
    classRepository shouldContain baz
  }
}