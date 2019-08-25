package uml.model

case object Modifiers {

  sealed trait Modifier

  case object Public extends Modifier

  case object Private extends Modifier

  case object Protected extends Modifier

  case object PackagePrivate extends Modifier

  case object Static extends Modifier

  case object Final extends Modifier

  case object Generic extends Modifier

  case object Abstract extends Modifier

  case object Default extends Modifier

  case object Volatile extends Modifier

  case object Synchronized extends Modifier

}