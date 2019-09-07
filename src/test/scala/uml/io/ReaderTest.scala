package uml.io

import org.scalatest.{FlatSpec, Matchers}

case class ReaderTest() extends FlatSpec with Matchers {
  "Reader" should "work" in {
    val file = getClass.getResource("/test/character/Character.java")
    val actual = Reader(file.getPath)

    actual shouldBe List(characterClass)
  }
}