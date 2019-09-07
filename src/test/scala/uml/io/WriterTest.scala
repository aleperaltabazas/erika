package uml.io

import org.scalatest.{FlatSpec, Matchers}

case class WriterTest() extends FlatSpec with Matchers {
  "Writer" should "work" in {
    val expectedWrite = "class Character {\nmaxHp: Double\ncurrentHp: Double\ngainHp(gain: Double)" +
      ": Void\nloseHp(damage: Double): Void\ngetCurrentHp(): Int\ngetMaxHp(): Double\ngainExp(exp: Double)" +
      ": Void\nsetMaxHp(maxHp: Double): Void\nsetCurrentHp(currentHp: Double): Void\n}"

    characterClass.write shouldBe expectedWrite
    characterClass.writeRelations shouldBe ""

    Writer.classDiagram(List(characterClass)) shouldBe s"@startuml\n$expectedWrite\n@enduml"
  }
}