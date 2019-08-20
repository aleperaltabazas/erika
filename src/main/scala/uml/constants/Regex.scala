package uml.constants

case object Regex {
  private val INHERITANCE: String = "extends \\w+"
  private val IMPLEMENTATION: String = "implements \\w+ ?(, ?\\w+ ?)*"

  val ANNOTATION: String = "@\\w+(\\n| )?"

  val VISIBILITY: String = "(public |private |protected )"

  val CLASS: String = s"($ANNOTATION)*($VISIBILITY)?(final )?class \\w+( $INHERITANCE)?( $IMPLEMENTATION)?"
  val ABSTRACT_CLASS: String = s"($ANNOTATION)*($VISIBILITY)?abstract class \\w+( $INHERITANCE)?( $IMPLEMENTATION)?"
  val INTERFACE: String = s"($ANNOTATION)*($VISIBILITY)?interface \\w+( $INHERITANCE)?"
  val ENUM: String = s"($ANNOTATION)*($VISIBILITY)?enum \\w+"

  val CLASS_DEFINITION: String = s"(.* )?(class|interface|enum) \\w+( .*)?"

  val CONSTRUCTOR: String => String = className => s"($ANNOTATION)*($VISIBILITY )?$className[(].*[)] ?[{]?"
}