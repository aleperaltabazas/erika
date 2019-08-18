package uml.constants

case object Regex {
  val CLASS_DEFINITION = "(public )?(abstract |final )?((class \\w+( extends \\w+)?( implements \\w+ ?(, ?\\w+ ?)*)? ?)|(interface \\w+( extends \\w+)?)|(enum \\w+( implements \\w+ ?(, ?\\w+ ?)*)?))[{]?"
  //basically: public? class <className>( extends <parent>)?( implements <interface>, <interface>...)? {
  val CLASS = "(\\s*@\\w+([(].*[)])?(\\s|\n)?\\s?)*(public )?class \\w+( extends \\w+)?( implements \\w+\\s?(,\\s?\\w+\\s?)*)?\\s?[{]?"
  //basically: public? abstract class <className>( extends <parent>)?( implements <interface>, <interface>...)? {
  val ABSTRACT = "(\\s*@\\w+([(].*[)])?(\\s|\n)?\\s?)*(public )?abstract class \\w+(<.*>)? (extends \\w+(<.*>)?)?( implements \\w+(<.*>)?\\s?(,\\s?\\w+(<.*>)?\\s?)*)?\\s?[{]"
  //basically: public? interface <interfaceName>( extends <parentInterface>)? {
  val INTERFACE = "(\\s*@\\w+([(].*[)])?(\\s|\n)?\\s?)*(public )?interface \\w+(<.*>)?( extends \\w+(<.*>)?\\s?(,\\s?\\w+(<.*>)?\\s?)*)?\\s?[{]"
  //basically: public? enum <enumName>( implements <interface>, <interface>...)?
  val ENUM = "(\\s*@\\w+([(].*[)])?(\\s|\n)?\\s?)*(public )?enum \\w+( implements \\w+\\s?(,\\s?\\w+\\s?)*)?\\s?[{]"
  val ANNOTATION = "\\s*@\\w+([(].*[)])?(\\s|\n)?\\s?"
  val METHOD = "\\s*(public |private |protected )?(static )?(\\w|[.]|<|>|,)+ \\w+\\s?[(].*[)]\\s?([{]?|;)\\s?"
  val ATTRIBUTE = "\\s*(@\\w+([(].*[)])?\\s?\n?)*\\s*(public |protected |private )?(static )?(final )?\\w(\\w|[.]|<|>|,)*\\s\\w+( ?);"
  val ENUM_CONSTANT: String = "(" + ANNOTATION + ")?" + "\\s*\\w+,?;?"
  val ENUM_CONSTANT_WITH_BEHAVIOR: String = "(" + ANNOTATION + ")?" + "\\s*\\w+(\\s?)([(].*[)])?\\s?[{]?;?\n?"
}