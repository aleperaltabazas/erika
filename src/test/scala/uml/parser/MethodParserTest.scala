package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.exception.ArgumentParseError
import uml.model.Modifiers.{Abstract, Protected, Public}
import uml.model.types.Type
import uml.model.{Argument, Method}

case class MethodParserTest() extends FlatSpec with Matchers {
  "parseArguments" should "work" in {
    MethodParser.parseArguments("(int foo, String bar)") shouldBe List(Argument("foo", Type of "int"),
      Argument("bar", Type of "String"))
    MethodParser.parseArguments("Map<String, String> foo, List<String> bar") shouldBe
      List(Argument("foo", Type of "Map<String|String>"), Argument("bar", Type of "List<String>"))
    MethodParser.parseArguments("") shouldBe List()
  }

  "parseArguments" should "fail" in {
    an[ArgumentParseError] should be thrownBy MethodParser.parseArguments("int foo, String")
  }

  "parse" should "work" in {
    MethodParser.parse(List("private int foo = 3", "public int getFoo()")) shouldBe List(
      Method("getFoo", Type of "int", List(), List(Public), List()))
    MethodParser.parse(List("private int foo;", "private int bar;", "public void doSomething(final int bar)",
      "public void getFoo()", "protected abstract List<String> getList()")) shouldBe List(
      Method("doSomething", Type of "void", List(Argument("bar", Type of "int")), List(Public), List()),
      Method("getFoo", Type of "void", List(), List(Public), List()),
      Method("getList", Type of "List<String>", List(), List(Protected, Abstract), List())
    )
  }
}