package uml.model

import org.scalatest.{FlatSpec, Matchers}
import uml.model.ClassTypes.{AbstractClass, ConcreteClass, Enum, Interface}

case class ClassTest() extends FlatSpec with Matchers {
  val foo = Class("Foo", Nil, Nil, Nil, Nil, None, Nil, ConcreteClass)
  val bar = Class("Bar", Nil, Nil, Nil, Nil, None, Nil, Interface)
  val baz = Class("Baz", Nil, Nil, Nil, Nil, None, Nil, AbstractClass)
  val biz = Class("Biz", Nil, Nil, Nil, Nil, None, Nil, Enum)

  "write" should "work with empty components list" in {
    foo.write shouldBe "class Foo {\n\n\n}"
    bar.write shouldBe "interface Bar {\n\n\n}"
    baz.write shouldBe "abstract class Baz {\n\n\n}"
    biz.write shouldBe "enum Biz {\n\n\n}"
  }
}