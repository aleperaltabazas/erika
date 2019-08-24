package uml.exception

case class IllegalExtensionError(className: String, parent: String, definition: String)
  extends RuntimeException(s"Parse error illegal self-extension found: $className cannot inherit from $parent in $definition")
