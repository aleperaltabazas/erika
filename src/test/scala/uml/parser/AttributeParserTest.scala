package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.model.Modifiers.{Final, Private, Static}
import uml.model.attributes.Attribute
import uml.model.types.{SimpleType, Type}
import uml.model.{Modifiers, attributes}

class AttributeParserTest extends FlatSpec with Matchers {
  "parse" should "work" in {
    AttributeParser.parseAttributes(List("private int foo = 3", "public int getFoo()")) shouldBe List(Attribute("foo", Type of
      "int", List(Modifiers.Private), List[String]()))
    AttributeParser.parseAttributes(List("private int foo;", "private int bar;", "public void " +
      "doSomething()", "public void getFoo()")) shouldBe List(
      attributes.Attribute("foo", Type of "int", List(Modifiers.Private), List()),
      attributes.Attribute("bar", Type of "int", List(Modifiers.Private), List()))
  }

  "parseAttribute" should "work" in {
    AttributeParser.parseAttribute("private int foo;") shouldBe Attribute("foo", SimpleType("Int"),
      List(Private), Nil)
    AttributeParser.parseAttribute("private static final String FOO = \"foo\";") shouldBe Attribute("FOO",
      SimpleType("String"), List(Private, Static, Final), Nil)
    AttributeParser.parseAttribute("@Autowired\n@Qualifier(\"foo\")\nprivate Foo foo;") shouldBe
      Attribute("foo", SimpleType("Foo"), List(Private), List("@Autowired", "@Qualifier(\"foo\")"))
  }
}