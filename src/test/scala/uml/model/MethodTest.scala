package uml.model

import org.scalatest.{FlatSpec, Matchers}
import uml.model.types.Type

case class MethodTest() extends FlatSpec with Matchers {
  val foo = Method("foo", Type of "int", Nil, Nil, Nil)
  val baz = Method("baz", Type of "void", List(Argument("a", Type of "String")), Nil, Nil)
  val biz = Method("biz", Type of "List<String>", List(Argument("a", Type of "List<String>"), Argument("b", Type of
    "Foo"), Argument("c", Type of "Bar")), Nil, Nil)

  "write" should "work" in {
    foo.write shouldBe "foo(): Int"
    baz.write shouldBe "baz(a: String): void"
    biz.write shouldBe "biz(a: List<String>, b: Foo, c: Bar): List<String>"
  }
}