package uml.parser

case object Parsers {

  sealed abstract class BoundedParser(leftBound: Char, rightBound: Char, check: Int => Boolean) {
    def apply(str: String): String = {
      var boundCheck = 0

      str.foldLeft("") {
        (acc, char) =>
          if (char == rightBound) boundCheck = boundCheck - 1

          val returnValue = if (check(boundCheck)) acc.appended(char)
          else acc

          if (char == leftBound) boundCheck = boundCheck + 1

          returnValue
      }
    }
  }

  case class Inner(left: Char, right: Char) extends BoundedParser(left, right, inner => inner > 0)

  case class Outer(left: Char, right: Char) extends BoundedParser(left, right, outer => outer == 0)

}
