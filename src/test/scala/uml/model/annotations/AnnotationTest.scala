package uml.model.annotations

import org.scalatest.{FlatSpec, Matchers}

case class AnnotationTest() extends FlatSpec with Matchers {
  "write" should "work" in {
    Annotation("Foo", Map()).write shouldBe "@Foo()"
    Annotation("Bar", Map(("value", "42"))).write shouldBe "@Bar(value = 42)"
    Annotation("Baz", Map(("foo", "42"), ("bar", "\"42\""), ("baz", "Constants.MEANING_OF_LIFE"))).write shouldBe
      "@Baz(foo = 42,bar = \"42\",baz = Constants.MEANING_OF_LIFE)"
  }
}