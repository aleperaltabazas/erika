package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.model.{Attribute, Modifiers}

class AttributeParserTest extends FlatSpec with Matchers {
  "parsing the following" should "work correctly" in {
    AttributeParser.parse(List("private int foo = 3", "public int getFoo()")) shouldBe List(Attribute("foo", "int",
      List(Modifiers.Private), List[String]()))
    AttributeParser.parse(List("private int foo;", "private int bar;", "public void " +
      "doSomething()", "public void getFoo()")) shouldBe List(Attribute("foo", "int", List(Modifiers.Private), List()),
      Attribute("bar", "int", List(Modifiers.Private), List()))
  }
}