package uml.model.types

case class GenericPlaceholder(genericClass: String, arity: Int) {
  def apply(genericType: String): GenericType = {
    val types = genericType.split(",").toList

    if (types.length != arity) {
      throw new IllegalArgumentException(s"Type $genericClass requires $arity generic types, received ${types.length}" +
        s" (given by $genericType)")
    } else {
      GenericType(genericClass, types.map(_type => Type of _type))
    }
  }
}