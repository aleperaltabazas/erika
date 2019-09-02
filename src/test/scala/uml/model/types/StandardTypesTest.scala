package uml.model.types

import org.scalatest.{FlatSpec, Matchers}

case class StandardTypesTest() extends FlatSpec with Matchers {
  "shouldBuildGeneric" should "be true" in {
    StandardTypes.shouldBuildGeneric("List") shouldBe true
    StandardTypes.shouldBuildGeneric("Optional") shouldBe true
    StandardTypes.shouldBuildGeneric("ArrayList") shouldBe true
  }
}
