package uml.model.methods

import org.scalatest.{FlatSpec, Matchers}
import uml.model.lang.Lang
import uml.model.types.{GenericType, SimpleType, StandardTypes, Type}

case class MethodTest() extends FlatSpec with Matchers {
  val foo = Method(
    name = "foo",
    outputType = StandardTypes.Int,
    arguments = Nil,
    modifiers = Nil,
    language = Lang.Java(Nil, Nil)
  )
  val baz = Method(
    name = "baz",
    outputType = StandardTypes.Void,
    arguments = List(Argument("a", StandardTypes.String)),
    modifiers = Nil,
    language = Lang.Java(Nil, Nil)
  )
  val biz = Method(
    name = "biz",
    outputType = GenericType("List", List(StandardTypes.String)),
    arguments = List(
      Argument("a", GenericType("List", List(StandardTypes.String))), Argument("b", SimpleType("Foo")), Argument("c", Type of "Bar")
    ),
    modifiers = Nil,
    language = Lang.Java(Nil, Nil)
  )

  "write" should "work" in {
    foo.write shouldBe "foo(): Int"
    baz.write shouldBe "baz(a: String): Void"
    biz.write shouldBe "biz(a: List<String>, b: Foo, c: Bar): List<String>"
  }
}
