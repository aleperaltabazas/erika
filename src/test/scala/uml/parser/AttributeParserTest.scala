package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.model.types.Type
import uml.model.{Attribute, Modifiers}

class AttributeParserTest extends FlatSpec with Matchers {
  "parse" should "work correctly" in {
    AttributeParser.parse(List("private int foo = 3", "public int getFoo()")) shouldBe List(Attribute("foo", Type of
      "int", List(Modifiers.Private), List[String]()))
    AttributeParser.parse(List("private int foo;", "private int bar;", "public void " +
      "doSomething()", "public void getFoo()")) shouldBe List(
      Attribute("foo", Type of "int", List(Modifiers.Private), List()),
      Attribute("bar", Type of "int", List(Modifiers.Private), List()))
  }
}