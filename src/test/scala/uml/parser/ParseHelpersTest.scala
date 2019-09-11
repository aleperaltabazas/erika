package uml.parser

import org.scalatest.{FlatSpec, Matchers}
import uml.parser.Parsers._

case class ParseHelpersTest() extends FlatSpec with Matchers {
  "Inner" should "work" in {
    val inParenthesis = Inner('(', ')')
    inParenthesis("ShouldNotParse(should parse) but not here!") shouldBe "should parse"
    inParenthesis("Failure(parses correctly(even with(nested delimiters)))but not outside") shouldBe
      "parses correctly(even with(nested delimiters))"
  }

  "Outer" should "work" in {
    val outsideParenthesis = Outer('(', ')')
    outsideParenthesis("ShouldParse(ignores here)ContinuesHere") shouldBe "ShouldParse()ContinuesHere"
    outsideParenthesis("Parses(and(ignores(nested)))") shouldBe "Parses()"
  }
}