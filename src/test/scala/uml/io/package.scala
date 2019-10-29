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
      ("GeneratedValue", Map())))),
      Attribute("maxHp", StandardTypes.Double, List(Private), Lang.Java(Nil)),
      Attribute("currentHp", StandardTypes.Double, List(Private), Lang.Java(Nil)),
      Attribute("level", SimpleType("Level"), List(Private), Lang.Java(Nil)),
      Attribute("currentLocation", SimpleType("Location"), List(Private), Lang.Java(List(Annotation("ManyToMany", Map())))),
      Attribute("characterState", SimpleType("CharacterState"), List(Private), Lang.Java(List(Annotation("OneToOne", Map())))),
      Attribute("inventory", SimpleType("Inventory"), List(Private), Lang.Java(List(Annotation("OneToOne", Map())))),
      Attribute("equippedWeapon", SimpleType("Weapon"), List(Private), Lang.Java(List(Annotation("OneToOne", Map()))))
    ),
    methods = List(
      Method("gainHp", StandardTypes.Void, List(Argument("gain", StandardTypes.Double, Nil, Lang.Java(Nil))), List
      (Public), Lang.Java(Nil)),
      Method("loseHp", StandardTypes.Void, List(Argument("damage", StandardTypes.Double, Nil, Lang.Java(Nil))), List(Public), Lang.Java(Nil)),
      Method("getCurrentHp", StandardTypes.Int, Nil, List(Public), Lang.Java(Nil)),
      Method("getMaxHp", StandardTypes.Double, Nil, List(Public), Lang.Java(Nil)),
      Method("gainExp", StandardTypes.Void, List(Argument("exp", StandardTypes.Double, Nil, Lang.Java(Nil))), List(Public),
        Lang.Java(Nil)),
      Method("setMaxHp", StandardTypes.Void, List(Argument("maxHp", StandardTypes.Double, Nil, Lang.Java(Nil))),
        List(Public), Lang.Java(Nil)),
      Method("setCurrentHp", StandardTypes.Void, List(Argument("currentHp", StandardTypes.Double, Nil, Lang.Java(Nil))),
        List(Public), Lang.Java(Nil)),
    ),
    modifiers = List(Public),
    parent = None,
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map())))
  )

  val characterStateClass = ActualClass(
    name = "CharacterState",
    attributes = List(Attribute("id", StandardTypes.Long, List(Private), Lang.Java(List(Annotation("Id", Map()),
      Annotation("GeneratedValue", Map()))))),
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"), Nil,
      Lang.Java(Nil))), List(Public, Abstract), Lang.Java(Nil))
    ),
    modifiers = List(Public, Abstract),
    parent = None,
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map()), Annotation("Inheritance", Map(("strategy", "InheritanceType.TABLE_PER_CLASS")))))
  )

  val aliveClass = ActualClass(
    name = "Alive",
    attributes = Nil,
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"), Nil,
      Lang.Java(Nil))),
      List(Public), Lang.Java(Nil))),
    modifiers = List(Public),
    parent = Some(characterStateClass),
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map())))
  )

  val knockedOutClass = ActualClass(
    name = "KnockedOut",
    attributes = Nil,
    methods = List(Method("call", SimpleType("CharacterState"), List(Argument("character", SimpleType("Character"), Nil,
      Lang.Java(Nil))), List(Public),
      Lang.Java(Nil))),
    modifiers = List(Public),
    parent = Some(characterStateClass),
    interfaces = Nil,
    language = Lang.Java(List(Annotation("Entity", Map())))
  )
}