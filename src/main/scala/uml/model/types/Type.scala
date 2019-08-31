package uml.model.types

trait Type {
  def name: String

  def matchesWith(name: String): Boolean = this.name == name
}

object Type {
  def of(string: String): Type = apply(string)

  def apply(_type: String): Type = {
    StandardTypes.get(_type).getOrElse {
      if (_type.matches(".*<.*>")) {
        val (wrappingType: String, composingTypes: List[Type]) = unwrap(_type)

        GenericType(wrappingType, composingTypes)
      }

      else SimpleType(_type)
    }
  }

  def unwrap(genericType: String): (String, List[Type]) = {
    val wrappingType: String = genericType.takeWhile(_ != '<')
    val composingTypes: List[Type] = genericType.substring(genericType.indexOf("<") + 1, genericType.length - 1)
      .split("[|]")
      .map(Type(_))
      .toList
    (wrappingType, composingTypes)
  }
}