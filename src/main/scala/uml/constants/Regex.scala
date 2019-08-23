package uml.constants

case object Regex {
  val GENERIC: String = "<.*>"
  private val INITIALIZATION: String = " ?= ?.*;?"
  private val INHERITANCE: String = s"extends [A-Z]\\w*($GENERIC)?"
  private val IMPLEMENTATION: String = s"implements [A-Z]\\w* ?(, ?[A-Z]\\w* ?)*($GENERIC)?"

  val ANNOTATION: String = "@[A-Z]\\w*(\\n| )?"

  val VISIBILITY: String = "(public|private|protected) ?"

  val MODIFIERS: String = "(static|final|volatile|synchronized) ?"

  val CLASS: String = s"($ANNOTATION)*($VISIBILITY)?(final )?class [A-Z]\\w*($GENERIC)?( $INHERITANCE)?( $IMPLEMENTATION)?"
  val ABSTRACT_CLASS: String = s"($ANNOTATION)*($VISIBILITY)?abstract class [A-Z]\\w*($GENERIC)?( $INHERITANCE)?( " +
    s"$IMPLEMENTATION)?"
  val INTERFACE: String = s"($ANNOTATION)*($VISIBILITY)?interface [A-Z]\\w*( $INHERITANCE)?"
  val ENUM: String = s"($ANNOTATION)*($VISIBILITY)?enum [A-Z]\\w*"

  val CLASS_DEFINITION: String = s"(.* )?(class|interface|enum) [A-Z]\\w*( .*)?"

  val CONSTRUCTOR: String => String = className => s"($ANNOTATION)*($VISIBILITY)?$className[(].*[)] ?[{]?"

  val TYPE: String = s"\\w+($GENERIC)?"

  val ATTRIBUTE: String = s"($ANNOTATION)*($VISIBILITY)($MODIFIERS)*$TYPE \\w+($INITIALIZATION|;?)"

  val METHOD: String = s"($ANNOTATION)*($VISIBILITY)($MODIFIERS)*$TYPE \\w+[(].*[)](;| ?{?)"

}