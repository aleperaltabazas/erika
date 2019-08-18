package uml.constants

import org.scalatest.{FlatSpec, Matchers}

class RegexTest extends FlatSpec with Matchers {
  "Class definition" should "match with the following" in {
    "public class Foo" matches Regex.CLASS_DEFINITION shouldBe true
    "public abstract class Foo" matches Regex.CLASS_DEFINITION shouldBe true
    "public interface Foo" matches Regex.CLASS_DEFINITION shouldBe true
    "public abstract final class Foo" matches Regex.CLASS_DEFINITION shouldBe false
    "class Foo extends Baz" matches Regex.CLASS_DEFINITION shouldBe true
    "public class Foo extends Bar implements Biz, Baz" matches Regex.CLASS_DEFINITION shouldBe true
  }
}