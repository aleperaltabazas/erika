package uml.model.attributes

import org.scalatest.{FlatSpec, Matchers}
import uml.model.Modifiers.Public
import uml.model.types.{GenericType, StandardTypes}

case class AttributeTest() extends FlatSpec with Matchers {
  val foo = Attribute(
    name = "foo",
    attributeType = StandardTypes.Int,
    modifiers = List(Public),
    annotations = Nil
  )
  val bar = Attribute(
    name = "bar",
    attributeType = GenericType("List", List(StandardTypes.String)),
    modifiers = List(Public),
    annotations = Nil
  )
  val baz = Attribute(
    name = "baz",
    attributeType = GenericType("Map", List(StandardTypes.String, GenericType("List", List(StandardTypes.String)))),
    modifiers = Nil,
    annotations = Nil
  )

  "write" should "work" in {
    foo.write shouldBe "foo: Int"
    bar.write shouldBe "bar: List<String>"
    baz.write shouldBe "baz: Map<String, List<String>>"
  }
}
