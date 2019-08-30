package uml.exception

case class IOException(msg: String) extends RuntimeException(msg)
