package uml.constants

import test.utils.StringMatcher

case class RegexTest() extends StringMatcher {
  "CONSTRUCTOR regex" should "match with the following" in {
    "Foo()" shouldMatch Regex.CONSTRUCTOR("Foo")
    "public Foo()" shouldMatch Regex.CONSTRUCTOR("Foo")
    "public Foo(String str) {" shouldMatch Regex.CONSTRUCTOR("Foo")
    "@Annotated public Foo(String str)" shouldMatch Regex.CONSTRUCTOR("Foo")
  }

}