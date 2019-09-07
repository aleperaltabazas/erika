package uml.model.types

case class SimpleType(name: String) extends Type {
  override def matchesWith(name: String): Boolean = this.name == name
}