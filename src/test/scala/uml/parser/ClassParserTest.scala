package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.builder.ClassBuilder
import uml.model.Modifiers._
import uml.model.attributes.Attribute
import uml.model.classes.ClassTypes
import uml.model.methods.{Argument, Method}
import uml.model.types.SimpleType

case class ClassParserTest() extends FlatSpec with Matchers {
  "classParserV2" should "work" in {
    ClassParser.parseClassToBuilder("class Foo {}") shouldBe ClassBuilder(
      name = "Foo",
      attributes = Nil,
      methods = Nil,
      modifiers = Nil,
      annotations = Nil,
      interfaces = Nil,
      classType = ClassTypes.ActualClass,
      declaredSuper = None,
      enumClauses = Nil
    )
    ClassParser.parseClassToBuilder("class Foo {\nprivate int foo;\n}") shouldBe ClassBuilder(
      name = "Foo",
      attributes = List(Attribute("foo", SimpleType("Int"), List(Private), Nil)),
      methods = Nil,
      modifiers = Nil,
      annotations = Nil,
      interfaces = Nil,
      classType = ClassTypes.ActualClass,
      declaredSuper = None,
      enumClauses = Nil
    )
    ClassParser.parseClassToBuilder("class Foo {\npublic int getFoo() {\nreturn 2;\n}\n}") shouldBe ClassBuilder(
      name = "Foo",
      attributes = Nil,
      methods = List(Method("getFoo", SimpleType("Int"), Nil, List(Public), Nil)),
      modifiers = Nil,
      annotations = Nil,
      interfaces = Nil,
      classType = ClassTypes.ActualClass,
      declaredSuper = None,
      enumClauses = Nil
    )
    ClassParser.parseClassToBuilder("public abstract class Foo {\npublic void foo(){\n}\n}\n}") shouldBe
      ClassBuilder(
        name = "Foo",
        attributes = Nil,
        methods = List(Method("foo", SimpleType("Void"), Nil, List(Public), Nil)),
        modifiers = List(Public, Abstract),
        annotations = Nil,
        interfaces = Nil,
        classType = ClassTypes.ActualClass,
        declaredSuper = None,
        enumClauses = Nil
      )
    ClassParser.parseClassToBuilder("@Data\npublic interface Bar {\nvoid doSomething();\ndefault int bar(int arg) " +
      "{\n}\n}") shouldBe
      ClassBuilder(
        name = "Bar",
        attributes = Nil,
        methods = List(
          Method(name = "doSomething", outputType = SimpleType("Void"), arguments = Nil, modifiers = Nil, annotations = Nil),
          Method(name = "bar", outputType = SimpleType("Int"), arguments = List(Argument("arg", SimpleType("Int"))),
            modifiers = List(Default), annotations = Nil)
        ),
        modifiers = List(Public),
        annotations = List("@Data"),
        interfaces = Nil,
        classType = ClassTypes.Interface,
        declaredSuper = None,
        enumClauses = Nil)
  }
}