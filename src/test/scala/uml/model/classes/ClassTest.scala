package uml.model.classes

import org.scalatest.{FlatSpec, Matchers}
import uml.model.Modifiers.{Private, Public}
import uml.model.attributes.Attribute
import uml.model.lang.Lang
import uml.model.methods.Method
import uml.model.types.{SimpleType, StandardTypes, Type}
import uml.model.{Modifiers, attributes, methods}

case class ClassTest() extends FlatSpec with Matchers {
  val foo = ActualClass(
    name = "Foo",
    attributes = Nil,
    methods = Nil,
    modifiers = Nil,
    parent = None,
    interfaces = Nil,
    language = Lang.Java(Nil, Nil)
  )
  val bar = Interface(
    name = "Bar",
    methods = Nil,
    modifiers = Nil,
    parent = None,
    language = Lang.Java(Nil, Nil)
  )
  val baz = ActualClass(
    name = "Baz",
    attributes = Nil,
    methods = Nil,
    modifiers = List(Modifiers.Abstract),
    parent = None,
    interfaces = Nil,
    language = Lang.Java(Nil, List(Modifiers.Abstract))
  )
  val biz = Enum(
    name = "Biz",
    attributes = Nil,
    methods = Nil,
    modifiers = Nil,
    interfaces = Nil,
    clauses = Nil,
    language = Lang.Java(Nil, Nil)
  )

  "write" should "work with empty components list" in {
    foo.write shouldBe "class Foo {\n\n\n}"
    bar.write shouldBe "interface Bar {\n\n\n}"
    baz.write shouldBe "abstract class Baz {\n\n\n}"
    biz.write shouldBe "enum Biz {\n\n\n}"
  }

  "writeRelations" should "work" in {
    val qux = ActualClass(
      name = "Qux",
      attributes = List(
        Attribute("foo", Type of "Foo", List(Private), Lang.Java(Nil, List(Private))),
        attributes.Attribute("bar", SimpleType("Bar"), List(Private), Lang.Java(Nil, List(Private))),
        attributes.Attribute("baz", Type of "List<Baz>", List(Private), Lang.Java(Nil, List(Private))),
        attributes.Attribute("some", StandardTypes.Int, List(Private), Lang.Java(Nil, List(Private)))
      ),
      methods = List(
        Method("getFoo", SimpleType("Foo"), Nil, List(Public), Lang.Java(Nil, List(Public))),
        methods.Method("getSome", StandardTypes.Int, Nil, List(Public), Lang.Java(Nil, List(Public))),
        methods.Method("getBaz", Type of "List<Baz>", Nil, List(Public), Lang.Java(Nil, List(Public)))
      ),
      modifiers = List(Public),
      parent = None,
      interfaces = Nil,
      language = Lang.Java(Nil, List(Public))
    )

    foo.writeRelations shouldBe ""
    qux.writeRelations shouldBe "Qux --> Foo\nQux --> Bar\nQux --> \"*\" Baz"
  }
}
