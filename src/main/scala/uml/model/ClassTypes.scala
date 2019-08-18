package uml.model

case object ClassTypes {
  trait ClassType

  case object ConcreteClass extends ClassType
  case object AbstractClass extends ClassType
  case object Interface extends ClassType
  case object Enum extends ClassType
}