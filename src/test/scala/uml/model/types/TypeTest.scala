package uml.model.types

import org.scalatest.{FlatSpec, Matchers}

case class TypeTest() extends FlatSpec with Matchers {
  "Type of" should "be" in {
    Type("Integer") shouldBe SimpleType("Int")
    Type("int") shouldBe SimpleType("Int")
    Type("List<String>") shouldBe GenericType("List", List(SimpleType("String")))
    Type("Map<String|String>") shouldBe GenericType("Map", List(SimpleType("String"), SimpleType("String")))
    Type("Map<String|List<String>>") shouldBe GenericType("Map", List(SimpleType("String"), GenericType("List",
      List(SimpleType("String")))))
  }

  "Type name" should "be" in {
    SimpleType("Integer").name shouldBe "Integer"
    GenericType("List", List(SimpleType("String"))).name shouldBe "List<String>"
    GenericType("Map", List(SimpleType("String"), SimpleType("String"))).name shouldBe "Map<String, String>"
    GenericType("Map", List(SimpleType("String"), GenericType("List", List(SimpleType("String"))))).name shouldBe
      "Map<String, List<String>>"
    (Type of "Integer").name shouldBe "Int"
    (Type of "double").name shouldBe "Double"
    (Type of "List").name shouldBe "List<Any>"
    (Type of "Map").name shouldBe "Map<Any, Any>"
  }
}
