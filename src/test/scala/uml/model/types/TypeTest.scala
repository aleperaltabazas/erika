package uml.model.types

import org.scalatest.{FlatSpec, Matchers}

case class TypeTest() extends FlatSpec with Matchers {
  "Type of" should "be" in {
    Type("Integer") shouldBe StandardTypes.Int
    Type("int") shouldBe StandardTypes.Int
    Type("List<String>") shouldBe GenericType("List", List(StandardTypes.String))
    Type("Map<String|String>") shouldBe GenericType("Map", List(StandardTypes.String, StandardTypes.String))
    Type("Map<String|List<String>>") shouldBe GenericType("Map", List(StandardTypes.String, GenericType("List",
      List(StandardTypes.String))))
  }

  "Type name" should "be" in {
    StandardTypes.Int.name shouldBe "Int"
    GenericType("List", List(StandardTypes.String)).name shouldBe "List<String>"
    GenericType("Map", List(StandardTypes.String, StandardTypes.String)).name shouldBe "Map<String, String>"
    GenericType("Map", List(StandardTypes.String, GenericType("List", List(StandardTypes.String)))).name shouldBe
      "Map<String, List<String>>"
    (Type of "Integer").name shouldBe "Int"
    (Type of "double").name shouldBe "Double"
    (Type of "List").name shouldBe "List<Any>"
    (Type of "Map").name shouldBe "Map<Any, Any>"
  }
}
