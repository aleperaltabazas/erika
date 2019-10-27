package uml

import uml.model.Modifiers.{Abstract, Private, Public}
import uml.model.attributes.Attribute
import uml.model.classes.ActualClass
import uml.model.methods.{Argument, Method}
import uml.model.types.{SimpleType, StandardTypes}

package object io {
  val characterClass: ActualClass = ActualClass(
    name = "Character",
    attributes = List(
      Attribute("id", StandardTypes.Long, List(Private), List("@Id()", "@GeneratedValue()")),
      Attribute("maxHp", StandardTypes.Double, List(Private), Nil),
      Attribute("currentHp", StandardTypes.Double, List(Private), Nil),
      Attribute("level", SimpleType("Level"), List(Private), Nil),
      Attribute("currentLocation", SimpleType("Location"), List(Private), List("@ManyToMany()")),
      Attribute("characterState", SimpleType("CharacterState"), List(Private), List("@OneToOne()")),
      Attribute("inventory", SimpleType("Inventory"), List(Private), List("@OneToOne()")),
      Attribute("equippedWeapon", SimpleType("Weapon"), List(Private), List("@OneToOne()"))
    ),
    methods = List(
      Method("gainHp", StandardTypes.Void, List(Argument("gain", StandardTypes.Double)), List(Public), Nil),
      Method("loseHp", StandardTypes.Void, List(Argument("damage", StandardTypes.Double)), List
      (Public), Nil),
      Method("getCurrentHp", StandardTypes.Int, Nil, List(Public), Nil),
      Method("getMaxHp", StandardTypes.Double, Nil, List(Public), Nil),
      Method("gainExp", StandardTypes.Void, List(Argument("exp", StandardTypes.Double)), List(Public),
        Nil),
      Method("setMaxHp", StandardTypes.Void, List(Argument("maxHp", StandardTypes.Double)), List(Public), Nil),
      Method("setCurrentHp", StandardTypes.Void, List(Argument("currentHp", StandardTypes.Double)), List(Public), Nil),
    ),
    modifiers = List(
      Public
    ), annotations = List("@Entity()"),
    parent = None,
    interfaces = Nil
  )

  val characterStateClass = ActualClass(
    name = "CharacterState",
    attributes = List(Attribute("id", StandardTypes.Long, List(Private), List("@Id()", "@GeneratedValue()"))),
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"))),
      List(Public, Abstract), Nil)
    ),
    modifiers = List(Public, Abstract),
    annotations = List("@Entity()", "@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)"),
    parent = None,
    interfaces = Nil
  )

  val aliveClass = ActualClass(
    name = "Alive",
    attributes = Nil,
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"))),
      List(Public), Nil)), List(Public),
    annotations = List("@Entity()"),
    parent = Some(characterStateClass),
    interfaces = Nil
  )

  val knockedOutClass = ActualClass(
    name = "KnockedOut",
    attributes = Nil,
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"))), List(Public), Nil)),
    modifiers = List(Public),
    annotations = List("@Entity()"),
    parent = Some(characterStateClass),
    interfaces = Nil
  )
}