package uml.exception

case class NoSuchTypeException(definition: String)
  extends RuntimeException(s"No class type could be parsed in $definition")
