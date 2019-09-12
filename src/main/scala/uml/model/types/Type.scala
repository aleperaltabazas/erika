package uml.model.types

import uml.utils.Implicits.RichString

trait Type {
  def name: String

  def matchesWith(name: String): Boolean
}

object Type {
  def of(string: String): Type = apply(string)

  def apply(_type: String): Type = {
    if (_type.matches(".*<.*>")) {
      val (wrappingType: String, composingTypes: List[Type]) = unwrap(_type)

      GenericType(wrappingType, composingTypes)
    } else if (StandardTypes.shouldBuildGeneric(_type)) {
      val placeholder = StandardTypes.getPlaceholder(_type).getOrElse {
        throw new IllegalArgumentException(s"Tried to " +
          s"build ${_type} as generic, but no matching placeholder was found")
      }
      val any = List.fill(placeholder.arity)(StandardTypes.ANY)
      placeholder(any)
    } else {
      StandardTypes.getType(_type).getOrElse(SimpleType(_type))
    }
  }

  def unwrap(genericType: String): (String, List[Type]) = {
    val wrappingType: String = genericType.takeWhile(_ != '<')
    val composingTypes: List[Type] = genericType.drop(genericType.indexOf("<") + 1).dropRight(1)
      .splitBy("[|]")
      .map(Type(_))
    (wrappingType, composingTypes)
  }
}