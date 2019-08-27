package test.utils

import org.scalatest.{Assertion, FlatSpec, Matchers}
import uml.repository.AbstractRepository

object Implicits extends FlatSpec with Matchers {

  implicit class RichString(str: String) {
    def shouldMatch(regex: String): Assertion = {
      str matches regex shouldBe true
    }
  }

  implicit class RichRepository[T](repository: AbstractRepository[T]) {
    def shouldContain(t: T): Assertion = {
      repository.getAll.contains(t) shouldBe true
    }

    def shouldBeSize(amount: Int): Assertion = {
      repository.getAll.size shouldEqual amount
    }
  }

  implicit class RichList[T](list: List[T]) {
    def shouldContain(t: T): Assertion = {
      list.contains(t) shouldBe true
    }
  }

}