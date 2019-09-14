package uml.constants

import org.scalatest.{FlatSpec, Matchers}
import test.utils.Implicits._

case class RegexTest() extends FlatSpec with Matchers {
  "CONSTRUCTOR regex" should "match with the following" in {
    "Foo()" shouldMatch Regex.CONSTRUCTOR("Foo")
    "public Foo()" shouldMatch Regex.CONSTRUCTOR("Foo")
    "public Foo(String str) " shouldMatch Regex.CONSTRUCTOR("Foo")
    "@Annotated public Foo(String str)" shouldMatch Regex.CONSTRUCTOR("Foo")
  }

}