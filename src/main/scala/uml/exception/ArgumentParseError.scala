package uml.exception

case class ArgumentParseError(msg: String)
  extends RuntimeException(s"ArgumentParseError: $msg")