package uml.model

import org.scalatest.{FlatSpec, Matchers}

case class TypeTest() extends FlatSpec with Matchers {
  "Type of" should "be" in {
    Type("Integer") shouldBe SimpleType("Integer")
    Type("int") shouldBe SimpleType("int")
    Type("List<String>") shouldBe GenericType("List", List(SimpleType("String")))
    Type("Map<String|String>") shouldBe GenericType("Map", List(SimpleType("String"), SimpleType("String")))
    Type("Map<String|List<String>>") shouldBe GenericType("Map", List(SimpleType("String"), GenericType("List", List
    (SimpleType("String")))))
  }
}