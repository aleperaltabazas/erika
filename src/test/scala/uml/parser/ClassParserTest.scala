package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.exception.NoClassDefinitionError

case class ClassParserTest() extends FlatSpec with Matchers {
  val classText: String = "public class Foo {\nprivate int foo;\nprivate int bar;\n public void doSomething() {\n " +
    "System.out.println(\"Hello, world!\");\n}\npublic void getFoo() {\n return foo;\n}\n}"
  val interfaceText: String = "public interface Foo {\n void doSomething();\n int getSomething();\n}"
  val enumText: String = "public enum Foo {\nFOO,\nBAR,\nBAZ,\n}"
  val abstractClassText: String = "public abstract class Foo {\nprivate int foo;\n protected abstract void " +
    "doSomethingAbstract()\n}"
  val missingClass: String = "public Foo {\n private int foo;\n}"
  val missingName: String = "public class {\n private int foo;\n}"
  val missingNameExtends: String = "public class extends Foo {\n private int foo;\n}"
  val classWithConstructor: String = "public class Foo {\npublic Foo(String str) {\nthis.str = str;\n}\nprivate " +
    "String str;\npublic String getStr() {\nreturn str;\n}\n}"
  val classInitializingVariable: String = "public class Foo {\nprivate int foo = 3;\npublic int getFoo() {\nreturn" +
    "foo;\n}\n}"

  "parseDefinition" should "work when class, interface or enum are present" in {
    ClassParser.parseDefinition(classText.split("\n")) shouldBe "public class Foo {"
    ClassParser.parseDefinition(interfaceText.split("\n")) shouldBe "public interface Foo {"
    ClassParser.parseDefinition(enumText.split("\n")) shouldBe "public enum Foo {"
    ClassParser.parseDefinition(abstractClassText.split("\n")) shouldBe "public abstract class Foo {"
  }

  "parseDefinition" should "fail with the following" in {
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName(missingClass)
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName(missingName)
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName(missingNameExtends)
  }

  "parseName" should "work with the following" in {
    ClassParser.parseName("public class Foo extends Bar") shouldBe "Foo"
    ClassParser.parseName("enum Baz") shouldBe "Baz"
    ClassParser.parseName("public interface Biz")
    ClassParser.parseName("abstract public class Bar") shouldBe "Bar"
  }

  "parseName" should "fail with the following" in {
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName("public class")
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName("public class extends Baz")
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName("public Foo extends bar")
  }

  "parseBody" should "yield the following" in {
    ClassParser.parseBody("Foo", classText) shouldBe List("private int foo", "private int bar", "public void " +
      "doSomething()", "public void getFoo()")
    ClassParser.parseBody("Foo", interfaceText) shouldBe List("void doSomething()", "int getSomething()")
    ClassParser.parseBody("Foo", enumText) shouldBe List("FOO,", "BAR,", "BAZ,")
    ClassParser.parseBody("Foo", abstractClassText) shouldBe List("private int foo", "protected abstract void " +
      "doSomethingAbstract()")
    ClassParser.parseBody("Foo", classWithConstructor) shouldBe List("private String str", "public String getStr()")
    ClassParser.parseBody("Foo", classInitializingVariable) shouldBe List("private int foo = 3", "public int getFoo()")
  }

  "parseAnnotations" should "yield the following" in {
    ClassParser.parseAnnotations("@Some @Annotation\n@NewLineAnnotation") shouldBe List("@Some", "@Annotation",
      "@NewLineAnnotation")
  }
}