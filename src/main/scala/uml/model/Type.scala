package uml.model

trait Type {
  def name: String
}

object Type {
  def of(string: String): Type = apply(string)

  def apply(string: String): Type = {
    if (string.matches(".*<.*>")) {
      val wrappingType: String = string.substring(0, string.indexOf("<"))
      val composingTypes: List[Type] = string.substring(string.indexOf("<") + 1, string.length - 1)
        .split("[|]")
        .map(Type(_))
        .toList

      GenericType(wrappingType, composingTypes)
    }
    else SimpleType(string)
  }
}