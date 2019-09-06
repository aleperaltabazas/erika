package uml.io

import org.scalatest.{FlatSpec, Matchers}
import uml.model.ClassTypes.ConcreteClass
import uml.model.Modifiers.{Private, Public}
import uml.model.types.SimpleType
import uml.model.{Argument, Attribute, Class, Method}

case class ReaderTest() extends FlatSpec with Matchers {
  "Reader" should "work" in {
    val file = getClass.getResource("/test/character/Character.java")
    val actual = Reader(file.getPath)

    val expected = Class("Character", List(
      Attribute("id", SimpleType("Long"), List(Private), List("@Id", "@GeneratedValue")),
      Attribute("maxHp", SimpleType("Double"), List(Private), Nil),
      Attribute("currentHp", SimpleType("Double"), List(Private), Nil),
      Attribute("level", SimpleType("Level"), List(Private), Nil),
      Attribute("currentLocation", SimpleType("Location"), List(Private), List("@ManyToMany")),
      Attribute("characterState", SimpleType("CharacterState"), List(Private), List("@OneToOne")),
      Attribute("inventory", SimpleType("Inventory"), List(Private), List("@OneToOne")),
      Attribute("equippedWeapon", SimpleType("Weapon"), List(Private), List("@OneToOne"))
    ), List(
      Method("gainHp", SimpleType("Void"), List(Argument("gain", SimpleType("Double"))), List(Public), Nil),
      Method("loseHp", SimpleType("Void"), List(Argument("damage", SimpleType("Double"))), List(Public), Nil),
      Method("getCurrentHp", SimpleType("Int"), Nil, List(Public), Nil),
      Method("getMaxHp", SimpleType("Double"), Nil, List(Public), Nil),
      Method("gainExp", SimpleType("Void"), List(Argument("exp", SimpleType("Double"))), List(Public), Nil),
      Method("setMaxHp", SimpleType("Void"), List(Argument("maxHp", SimpleType("Double"))), List(Public), Nil),
      Method("setCurrentHp", SimpleType("Void"), List(Argument("currentHp", SimpleType("Double"))), List(Public), Nil),
    ), List(
      Public
    ), List("@Entity"),
      None,
      Nil,
      ConcreteClass)

    actual shouldBe List(expected)
  }
}