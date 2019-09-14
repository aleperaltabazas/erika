package uml.model

import org.scalatest.{FlatSpec, Matchers}
import uml.model.Modifiers.Public
import uml.model.attributes.Attribute
import uml.model.types.Type

case class AttributeTest() extends FlatSpec with Matchers {
  val foo = Attribute("foo", Type of "int", List(Public), Nil)
  val bar = attributes.Attribute("bar", Type of "List<String>", List(Public), Nil)
  val baz = attributes.Attribute("baz", Type of "Map<String|List<String>>", Nil, Nil)

  "write" should "work" in {
    foo.write shouldBe "foo: Int"
    bar.write shouldBe "bar: List<String>"
    baz.write shouldBe "baz: Map<String, List<String>>"
  }
}