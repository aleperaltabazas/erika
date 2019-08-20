package uml.constants

case object Regex {
  private val INHERITANCE: String = "extends [A-Z]\\w*"
  private val IMPLEMENTATION: String = "implements [A-Z]\\w* ?(, ?[A-Z]\\w* ?)*"

  val ANNOTATION: String = "@[A-Z]\\w*(\\n| )?"

  val VISIBILITY: String = "(public |private |protected )"

  val CLASS: String = s"($ANNOTATION)*($VISIBILITY)?(final )?class [A-Z]\\w*( $INHERITANCE)?( $IMPLEMENTATION)?"
  val ABSTRACT_CLASS: String = s"($ANNOTATION)*($VISIBILITY)?abstract class [A-Z]\\w*( $INHERITANCE)?( $IMPLEMENTATION)?"
  val INTERFACE: String = s"($ANNOTATION)*($VISIBILITY)?interface [A-Z]\\w*( $INHERITANCE)?"
  val ENUM: String = s"($ANNOTATION)*($VISIBILITY)?enum [A-Z]\\w*"

  val CLASS_DEFINITION: String = s"(.* )?(class|interface|enum) [A-Z]\\w*( .*)?"

  val CONSTRUCTOR: String => String = className => s"($ANNOTATION)*($VISIBILITY )?$className[(].*[)] ?[{]?"
}