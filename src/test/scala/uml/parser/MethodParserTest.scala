package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.model.{Argument, Type}

case class MethodParserTest() extends FlatSpec with Matchers {
  "parseArguments" should "work with the following" in {
    MethodParser.parseArguments(List("(int foo,", "String bar)")) shouldBe List(Argument("foo", Type of "int"),
      Argument("bar", Type of "String"))
    MethodParser.parseArguments(List("Map<String, String> foo,", "List<String> bar")) shouldBe
      List(Argument("foo", Type of "Map<String|String>"), Argument("bar", Type of "List<String>"))
  }
}