package uml.model

import org.scalatest.{FlatSpec, Matchers}
import uml.model.Modifiers.{Private, Public}
import uml.model.attributes.Attribute
import uml.model.classes.{ActualClass, Enum, Interface}
import uml.model.methods.Method
import uml.model.types.{SimpleType, StandardTypes, Type}

case class ClassTest() extends FlatSpec with Matchers {
  val foo = ActualClass(
    name = "Foo",
    attributes = Nil,
    methods = Nil,
    modifiers = Nil,
    annotations = Nil,
    parent = None,
    interfaces = Nil
  )
  val bar = Interface(
    name = "Bar",
    methods = Nil,
    modifiers = Nil,
    annotations = Nil,
    parent = None
  )
  val baz = ActualClass(
    name = "Baz",
    attributes = Nil,
    methods = Nil,
    modifiers = List(Modifiers.Abstract),
    annotations = Nil,
    parent = None,
    interfaces = Nil
  )
  val biz = Enum(
    name = "Biz",
    attributes = Nil,
    methods = Nil,
    modifiers = Nil,
    annotations = Nil,
    interfaces = Nil,
    clauses = Nil
  )

  "write" should "work with empty components list" in {
    foo.write shouldBe "class Foo {\n\n\n}"
    bar.write shouldBe "interface Bar {\n\n\n}"
    baz.write shouldBe "abstract class Baz {\n\n\n}"
    biz.write shouldBe "enum Biz {\n\n\n}"
  }

  "writeRelations" should "work" in {
    val qux = ActualClass("Qux", List(
      Attribute("foo", Type of "Foo", List(Private), Nil),
      attributes.Attribute("bar", SimpleType("Bar"), List(Private), Nil),
      attributes.Attribute("baz", Type of  "List<Baz>", List(Private), Nil),
      attributes.Attribute("some", StandardTypes.Int, List(Private), Nil)
    ), List(
      Method("getFoo", SimpleType("Foo"), Nil, List(Public), Nil),
      methods.Method("getSome", StandardTypes.Int, Nil, List(Public), Nil),
      methods.Method("getBaz", Type of "List<Baz>", Nil, List(Public), Nil)
    ), List(Public),
      Nil,
      None,
      Nil)

    foo.writeRelations shouldBe ""
    qux.writeRelations shouldBe "Qux --> Foo\nQux --> Bar\nQux --> \"*\" Baz"
  }
}