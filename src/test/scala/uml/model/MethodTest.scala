package uml.model

import org.scalatest.{FlatSpec, Matchers}
import uml.model.methods.{Argument, Method}
import uml.model.types.Type

case class MethodTest() extends FlatSpec with Matchers {
  val foo = Method("foo", Type of "int", Nil, Nil, Nil)
  val baz = methods.Method("baz", Type of "void", List(Argument("a", Type of "String")), Nil, Nil)
  val biz = methods.Method("biz", Type of "List<String>", List(methods.Argument("a", Type of "List<String>"), methods.Argument("b", Type of
    "Foo"), methods.Argument("c", Type of "Bar")), Nil, Nil)

  "write" should "work" in {
    foo.write shouldBe "foo(): Int"
    baz.write shouldBe "baz(a: String): Void"
    biz.write shouldBe "biz(a: List<String>, b: Foo, c: Bar): List<String>"
  }
}