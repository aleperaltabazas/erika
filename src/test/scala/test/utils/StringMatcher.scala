package test.utils

import org.scalatest.{Assertion, FlatSpec, Matchers}

class StringMatcher() extends FlatSpec with Matchers {

  implicit class RichString(str: String) {
    def shouldMatch(regex: String): Assertion = {
      str matches regex shouldBe true
    }
  }

}