package uml.model.types

case object StandardTypes {
  private val INT: Type = Type of "Int"
  private val CHAR: Type = Type of "Character"
  private val DOUBLE: Type = Type of "Double"
  private val STRING: Type = Type of "String"
  private val FLOAT: Type = Type of "Float"
  private val BOOL: Type = Type of "Boolean"
  private val LONG: Type = Type of "Long"
  private val SHORT: Type = Type of "Short"
  private val VOID: Type = Type of "void"
  private val BYTE: Type = Type of "Byte"

  def get(name: String): Option[Type] = name match {
    case "int" | "Integer" => Option(INT)
    case "char" | "Character" => Option(CHAR)
    case "double" | "Double" => Option(DOUBLE)
    case "String" => Option(STRING)
    case "float" | "FLOAT" => Option(FLOAT)
    case "boolean" | "Boolean" => Option(BOOL)
    case "long" | "Long" => Option(LONG)
    case "short" | "Short" => Option(SHORT)
    case "void" | "Void" => Option(VOID)
    case "byte" | "Byte" => Option(BYTE)
    case _ => None
  }
}