package uml.exception

case class NoSuchModifierException(str: String) extends RuntimeException(s"No such modifier $str")
