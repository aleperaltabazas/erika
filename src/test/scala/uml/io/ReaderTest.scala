package uml.io

import org.scalatest.{FlatSpec, Matchers}

case class ReaderTest() extends FlatSpec with Matchers {
  "Reader" should "work with a file" in {
    val file = getClass.getResource("/test/character/Character.java")
    val actual = Reader(file.getPath)

    actual shouldBe List(characterClass)
  }

  "Reader" should "work with a directory" in {
    val dir = getClass.getResource("/test/character/state")
    val actual = Reader(dir.getPath)

    actual shouldBe List(aliveClass, knockedOutClass, characterStateClass)
  }
}