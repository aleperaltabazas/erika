package uml.exception

case class MethodParseError(msg: String) extends RuntimeException(s"MethodParseError: $msg")
