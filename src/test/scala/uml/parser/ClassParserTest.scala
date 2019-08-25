package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.exception.{IllegalExtensionError, NoClassDefinitionError, NoSuchTypeException}
import uml.model.ClassTypes._
import uml.model.Modifiers._

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

  "parseDefinition" should "work" in {
    ClassParser.parseDefinition(classText.split("\n")) shouldBe "public class Foo"
    ClassParser.parseDefinition(interfaceText.split("\n")) shouldBe "public interface Foo"
    ClassParser.parseDefinition(enumText.split("\n")) shouldBe "public enum Foo"
    ClassParser.parseDefinition(abstractClassText.split("\n")) shouldBe "public abstract class Foo"
  }

  "parseDefinition" should "fail" in {
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName(missingClass)
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName(missingName)
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName(missingNameExtends)
  }

  "parseName" should "work" in {
    ClassParser.parseName("public class Foo extends Bar") shouldBe "Foo"
    ClassParser.parseName("enum Baz") shouldBe "Baz"
    ClassParser.parseName("public interface Biz")
    ClassParser.parseName("abstract public class Bar") shouldBe "Bar"
    ClassParser.parseName("public class Foo<T>") shouldBe "Foo"
  }

  "parseName" should "fail" in {
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName("public class")
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName("public class extends Baz")
    a[NoClassDefinitionError] should be thrownBy ClassParser.parseName("public Foo extends bar")
  }

  "parseBody" should "work" in {
    ClassParser.parseBody("Foo", classText) shouldBe List("private int foo", "private int bar", "public void " +
      "doSomething()", "public void getFoo()")
    ClassParser.parseBody("Foo", interfaceText) shouldBe List("void doSomething()", "int getSomething()")
    ClassParser.parseBody("Foo", enumText) shouldBe List("FOO,", "BAR,", "BAZ,")
    ClassParser.parseBody("Foo", abstractClassText) shouldBe List("private int foo", "protected abstract void " +
      "doSomethingAbstract()")
    ClassParser.parseBody("Foo", classWithConstructor) shouldBe List("private String str", "public String getStr()")
    ClassParser.parseBody("Foo", classInitializingVariable) shouldBe List("private int foo = 3", "public int getFoo()")
  }

  "parseAnnotations" should "work" in {
    ClassParser.parseAnnotations("@Some @Annotation\n@NewLineAnnotation") shouldBe List("@Some", "@Annotation",
      "@NewLineAnnotation")
    ClassParser.parseAnnotations("@Some @Annotation\n@NewLineAnnotation\npublic class Foo") shouldBe List("@Some", "@Annotation", "@NewLineAnnotation")
    ClassParser.parseAnnotations("@Some @Annotation\n@NewLineAnnotation\npublic class Foo " +
      "{\n@ShouldNotBePresent\nprivate int foo;\n}") shouldBe List("@Some", "@Annotation", "@NewLineAnnotation")
  }

  "parseType" should "work" in {
    ClassParser.parseType("Foo", "public class Foo".split("\\s").toList) shouldBe ConcreteClass
    ClassParser.parseType("Foo", "public abstract class Foo".split("\\s").toList) shouldBe AbstractClass
    ClassParser.parseType("Foo", "interface Foo extends Bar".split("\\s").toList) shouldBe Interface
    ClassParser.parseType("Foo", "public enum Foo".split("\\s").toList) shouldBe Enum
  }

  "parseType" should "fail" in {
    a[NoSuchTypeException] should be thrownBy ClassParser.parseType("Foo", "public abstract Foo".split("\\s").toList)
  }

  "parseModifiers" should "work" in {
    ClassParser.parseModifiers(ConcreteClass, "public final class Foo".split("\\s").toList) shouldBe List(Public, Final)
    ClassParser.parseModifiers(AbstractClass, "abstract public class Foo".split("\\s").toList) shouldBe List(Abstract, Public)
    ClassParser.parseModifiers(Interface, "public interface Foo".split("\\s").toList) shouldBe List(Public)
  }

  "parseInterfaces" should "work" in {
    ClassParser.parseInterfaces("Foo", "public class Foo".split("\\s").toList) shouldBe List()
    ClassParser.parseInterfaces("Foo", "public class Foo implements Bar".split("\\s").toList) shouldBe List("Bar")
    ClassParser.parseInterfaces("Foo", "public class Foo implements Bar, Baz, Biz".split("\\s").toList) shouldBe
      List("Bar", "Baz", "Biz")
  }

  "parseSuper" should "work" in {
    ClassParser.parseSuper("Foo", "public class Foo".split("\\s").toList) shouldBe None
    ClassParser.parseSuper("Foo", "public class Foo extends Bar".split("\\s").toList) shouldBe Some("Bar")
    an[IllegalExtensionError] should be thrownBy ClassParser.parseSuper("Foo", "public class Foo extends Foo".split
    ("\\s").toList)
  }
}