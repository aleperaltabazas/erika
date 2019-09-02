package uml.exception

import uml.model.types.GenericPlaceholder

case class GenericCreationError(placeholder: GenericPlaceholder, actual: List[Any])
  extends RuntimeException(s"Type ${placeholder.genericClass} requires ${placeholder.arity} generic types, received ${actual.length}" +
    s" (given by $actual)")