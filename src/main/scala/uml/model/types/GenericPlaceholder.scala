package uml.model.types

import uml.exception.GenericCreationError

case class GenericPlaceholder(genericClass: String, arity: Int) {
  def apply(genericType: String): GenericType = {
    val types = genericType.split(",").toList

    if (types.length != arity) {
      throw GenericCreationError(this, types)
    } else {
      GenericType(genericClass, types.map(_type => Type of _type))
    }
  }

  def apply(types: List[Type]): GenericType = {
    if (types.length != arity) {
      throw GenericCreationError(this, types)
    }

    GenericType(genericClass, types)
  }
}