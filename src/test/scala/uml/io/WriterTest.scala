package uml.io

import org.scalatest.{FlatSpec, Matchers}

case class WriterTest() extends FlatSpec with Matchers {
  val writer = Writer()

  "Writer" should "work" in {
    val expectedWrite = "class Character {\nmaxHp: Double\ncurrentHp: Double\ngainHp(gain: Double)" +
      ": Void\nloseHp(damage: Double): Void\ngainExp(exp: Double): Void\n}"
    val expectedRelations = "Character --> Level\nCharacter --> Location\nCharacter --> " +
      "CharacterState\nCharacter --> Inventory\nCharacter --> Weapon"

    characterClass.write shouldBe expectedWrite
    characterClass.writeRelations shouldBe expectedRelations

    writer.classDiagram(List(characterClass)) shouldBe s"@startuml\n$expectedWrite\n$expectedRelations@enduml"
  }
}