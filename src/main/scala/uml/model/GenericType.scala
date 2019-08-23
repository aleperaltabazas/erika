package uml.model

case class GenericType(wrappingType: String, composingTypes: List[Type]) extends Type {
  override def name: String = {
    val generic = composingTypes.map(_.name).mkString(", ")
    s"$wrappingType<{$generic}>"
  }
}