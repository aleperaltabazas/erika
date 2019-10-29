package uml

import uml.model.Modifiers.{Abstract, Private, Public}
import uml.model.annotations.Annotation
import uml.model.attributes.Attribute
import uml.model.classes.ActualClass
import uml.model.lang.Lang
import uml.model.methods.{Argument, Method}
import uml.model.types.{SimpleType, StandardTypes}

package object io {
  val characterClass: ActualClass = ActualClass(
    name = "Character",
    attributes = List(
      Attribute("id", StandardTypes.Long, List(Private), Lang.Java(List(Annotation("Id", Map()), Annotation
      ("GeneratedValue", Map())), List(Private))),
      Attribute("maxHp", StandardTypes.Double, List(Private), Lang.Java(Nil, List(Private))),
      Attribute("currentHp", StandardTypes.Double, List(Private), Lang.Java(Nil, List(Private))),
      Attribute("level", SimpleType("Level"), List(Private), Lang.Java(Nil, List(Private))),
      Attribute("currentLocation", SimpleType("Location"), List(Private), Lang.Java(List(Annotation("ManyToMany",
        Map())), List(Private))),
      Attribute("characterState", SimpleType("CharacterState"), List(Private), Lang.Java(List(Annotation("OneToOne", Map())),
        List(Private))),
      Attribute("inventory", SimpleType("Inventory"), List(Private), Lang.Java(List(Annotation("OneToOne", Map())),
        List(Private))),
      Attribute("equippedWeapon", SimpleType("Weapon"), List(Private), Lang.Java(List(Annotation("OneToOne", Map())),
        List(Private)))
    ),
    methods = List(
      Method("gainHp", StandardTypes.Void, List(Argument("gain", StandardTypes.Double)), List(Public), Lang.Java(Nil,
        List(Public))),
      Method("loseHp", StandardTypes.Void, List(Argument("damage", StandardTypes.Double)), List(Public), Lang.Java(Nil,
        List(Public))),
      Method("getCurrentHp", StandardTypes.Int, Nil, List(Public), Lang.Java(Nil, List(Public))),
      Method("getMaxHp", StandardTypes.Double, Nil, List(Public), Lang.Java(Nil, List(Public))),
      Method("gainExp", StandardTypes.Void, List(Argument("exp", StandardTypes.Double)), List(Public), Lang.Java(Nil, List(Public))),
      Method("setMaxHp", StandardTypes.Void, List(Argument("maxHp", StandardTypes.Double)), List(Public), Lang.Java(Nil, List(Public))),
      Method("setCurrentHp", StandardTypes.Void, List(Argument("currentHp", StandardTypes.Double)), List(Public), Lang.Java(Nil, List(Public))),
    ),
    modifiers = List(Public),
    parent = None,
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map())), List(Public))
  )

  val characterStateClass = ActualClass(
    name = "CharacterState",
    attributes = List(Attribute("id", StandardTypes.Long, List(Private), Lang.Java(List(Annotation("Id", Map()),
      Annotation("GeneratedValue", Map())), List(Private)))),
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"))),
      List(Public, Abstract), Lang.Java(Nil, List(Public, Abstract)))
    ),
    modifiers = List(Public, Abstract),
    parent = None,
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map()), Annotation("Inheritance", Map(("strategy", "InheritanceType.TABLE_PER_CLASS")))),
      List(Public, Abstract))
  )

  val aliveClass = ActualClass(
    name = "Alive",
    attributes = Nil,
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"))),
      List(Public), Lang.Java(Nil, List(Public)))),
    modifiers = List(Public),
    parent = Some(characterStateClass),
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map())), List(Public))
  )

  val knockedOutClass = ActualClass(
    name = "KnockedOut",
    attributes = Nil,
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"))), List(Public),
      Lang.Java(Nil, List(Public)))),
    modifiers = List(Public),
    parent = Some(characterStateClass),
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map())), List(Public))
  )
}