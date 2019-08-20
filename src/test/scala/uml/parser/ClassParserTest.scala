package uml.parser

import org.scalatest.{FlatSpec, Matchers}

case class ClassParserTest() extends FlatSpec with Matchers {
  val classText = "public class Foo {\nprivate int foo;\nprivate int bar;\n}"
  val interfaceText = "public interface Foo {\n void doSomething();\n int getSomething();\n}"
  val enumText = "public enum Foo {\nFOO,\nBAR,\nBAZ,\n}"
  val abstractClassText = "public abstract class Foo {\nprivate int foo;\n protected abstract void " +
    "doSomethingAbstract()\n}"

  "parseDefinition" should "work when class, interface or enum are present" in {
    ClassParser.parseDefinition(classText.split("\n")) shouldBe "public class Foo {"
    ClassParser.parseDefinition(interfaceText.split("\n")) shouldBe "public interface Foo {"
    ClassParser.parseDefinition(enumText.split("\n")) shouldBe "public enum Foo {"
    ClassParser.parseDefinition(abstractClassText.split("\n")) shouldBe "public abstract class Foo {"
  }
}