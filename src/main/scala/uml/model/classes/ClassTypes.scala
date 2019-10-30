package uml.model.classes

case object ClassTypes {

  trait ClassType {
    def write: String
  }

  case object ActualClass extends ClassType {
    override def write: String = "class"

    override def toString: String = "class"
  }

  case object Interface extends ClassType {
    override def write: String = "interface"

    override def toString: String = "interface"
  }

  case object Enum extends ClassType {
    override def write: String = "enum"

    override def toString: String = "enum"
  }

  case object WellKnownObject extends ClassType {
    override def write: String = "object"
  }

}