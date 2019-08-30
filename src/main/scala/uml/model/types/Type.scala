package uml.model.types

trait Type {
  def name: String
}

object Type {
  def of(string: String): Type = apply(string)

  def apply(string: String): Type = {
    if (string.matches(".*<.*>")) {
      val wrappingType: String = string.takeWhile(_ != '<')
      val composingTypes: List[Type] = string.substring(string.indexOf("<") + 1, string.length - 1)
        .split("[|]")
        .map(Type(_))
        .toList

      GenericType(wrappingType, composingTypes)
    }

    else SimpleType(string)
  }
}