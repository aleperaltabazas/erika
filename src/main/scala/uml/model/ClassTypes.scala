package uml.model

case object ClassTypes {

  trait ClassType

  case object ConcreteClass extends ClassType {
    override def toString: String = "class"
  }

  case object AbstractClass extends ClassType {
    override def toString: String = "class"
  }

  case object Interface extends ClassType {
    override def toString: String = "interface"
  }

  case object Enum extends ClassType {
    override def toString: String = "enum"
  }

}