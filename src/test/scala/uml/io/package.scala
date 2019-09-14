package uml

import uml.model.Modifiers.{Abstract, Private, Public}
import uml.model.attributes.Attribute
import uml.model.classes.ActualClass
import uml.model.methods.{Argument, Method}
import uml.model.types.SimpleType
import uml.model.{attributes, methods}

package object io {
  val characterClass: ActualClass = ActualClass("Character", List(
    Attribute("id", SimpleType("Long"), List(Private), List("@Id", "@GeneratedValue")),
    attributes.Attribute("maxHp", SimpleType("Double"), List(Private), Nil),
    attributes.Attribute("currentHp", SimpleType("Double"), List(Private), Nil),
    attributes.Attribute("level", SimpleType("Level"), List(Private), Nil),
    attributes.Attribute("currentLocation", SimpleType("Location"), List(Private), List("@ManyToMany")),
    attributes.Attribute("characterState", SimpleType("CharacterState"), List(Private), List("@OneToOne")),
    attributes.Attribute("inventory", SimpleType("Inventory"), List(Private), List("@OneToOne")),
    attributes.Attribute("equippedWeapon", SimpleType("Weapon"), List(Private), List("@OneToOne"))
  ), List(
    Method("gainHp", SimpleType("Void"), List(Argument("gain", SimpleType("Double"))), List(Public), Nil),
    methods.Method("loseHp", SimpleType("Void"), List(methods.Argument("damage", SimpleType("Double"))), List(Public), Nil),
    methods.Method("getCurrentHp", SimpleType("Int"), Nil, List(Public), Nil),
    methods.Method("getMaxHp", SimpleType("Double"), Nil, List(Public), Nil),
    methods.Method("gainExp", SimpleType("Void"), List(methods.Argument("exp", SimpleType("Double"))), List(Public), Nil),
    methods.Method("setMaxHp", SimpleType("Void"), List(methods.Argument("maxHp", SimpleType("Double"))), List(Public), Nil),
    methods.Method("setCurrentHp", SimpleType("Void"), List(methods.Argument("currentHp", SimpleType("Double"))), List(Public), Nil),
  ), List(
    Public
  ), List("@Entity"),
    None,
    Nil,
    false)

  val characterStateClass = ActualClass("CharacterState", List(
    attributes.Attribute("id", SimpleType("Long"), List(Private), List("@Id", "@GeneratedValue"))
  ), List(
    methods.Method("call", SimpleType("CharacterState"), List(methods.Argument("character", SimpleType("Character"))), List(Public,
      Abstract), Nil)
  ), List(
    Public, Abstract
  ), List("@Entity", "@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)"),
    None,
    Nil,
    true)

  val aliveClass = ActualClass("Alive", Nil, List(
    methods.Method("call", SimpleType("CharacterState"), List(methods.Argument("character", SimpleType("Character"))), List(Public), Nil)
  ), List(Public),
    List("@Entity"),
    Some(characterStateClass),
    Nil,
    false)

  val knockedOutClass = ActualClass("KnockedOut", Nil, List(
    methods.Method("call", SimpleType("CharacterState"), List(methods.Argument("character", SimpleType("Character"))), List(Public), Nil)
  ), List(Public),
    List("@Entity"),
    Some(characterStateClass),
    Nil,
    false)
}