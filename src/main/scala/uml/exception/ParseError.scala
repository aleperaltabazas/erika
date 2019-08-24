package uml.exception

case class ParseError(msg: String)
  extends RuntimeException(msg)
