package uml.exception

case class AttributeParseError(msg: String)
  extends RuntimeException(s"AttributeParseError: $msg")