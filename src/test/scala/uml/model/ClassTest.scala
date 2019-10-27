package uml.model

import org.scalatest.{FlatSpec, Matchers}
import uml.model.Modifiers.{Private, Public}
import uml.model.attributes.Attribute
import uml.model.classes.{ActualClass, Enum, Interface}
import uml.model.methods.Method
import uml.model.types.Type

case class ClassTest() extends FlatSpec with Matchers {
  val foo = ActualClass("Foo", Nil, Nil, Nil, Nil, None, Nil)
  val bar = Interface("Bar", Nil, Nil, Nil, None)
  val baz = ActualClass("Baz", Nil, Nil, Nil, Nil, None, Nil)
  val biz = Enum("Biz", Nil, Nil, Nil, Nil, Nil, Nil)

  "write" should "work with empty components list" in {
    foo.write shouldBe "class Foo {\n\n\n}"
    bar.write shouldBe "interface Bar {\n\n\n}"
    baz.write shouldBe "abstract class Baz {\n\n\n}"
    biz.write shouldBe "enum Biz {\n\n\n}"
  }

  "writeRelations" should "work" in {
    val qux = ActualClass("Qux", List(
      Attribute("foo", Type of "Foo", List(Private), Nil),
      attributes.Attribute("bar", Type of "Bar", List(Private), Nil),
      attributes.Attribute("baz", Type of "List<Baz>", List(Private), Nil),
      attributes.Attribute("some", Type of "Int", List(Private), Nil)
    ), List(
      Method("getFoo", Type of "Foo", Nil, List(Public), Nil),
      methods.Method("getSome", Type of "Int", Nil, List(Public), Nil),
      methods.Method("getBaz", Type of "List<Baz>", Nil, List(Public), Nil)
    ), List(Public),
      Nil,
      None,
      Nil)

    foo.writeRelations shouldBe ""
    qux.writeRelations shouldBe "Qux --> Foo\nQux --> Bar\nQux --> \"*\" Baz"
  }
}