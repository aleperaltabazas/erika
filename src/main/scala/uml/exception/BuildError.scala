package uml.exception

case class BuildError(msg: String) extends RuntimeException(msg)