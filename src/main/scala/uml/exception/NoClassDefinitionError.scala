package uml.exception

case class NoClassDefinitionError(msg: String)
  extends RuntimeException(msg)