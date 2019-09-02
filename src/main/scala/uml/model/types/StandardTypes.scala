package uml.model.types

case class StandardTypes() {
   val INT: Type = Type of "Int"
   val CHAR: Type = Type of "Character"
   val DOUBLE: Type = Type of "Double"
   val STRING: Type = Type of "String"
   val FLOAT: Type = Type of "Float"
   val BOOL: Type = Type of "Boolean"
   val LONG: Type = Type of "Long"
   val SHORT: Type = Type of "Short"
   val VOID: Type = Type of "void"
   val BYTE: Type = Type of "Byte"
   val ANY: Type = Type of "Any"
   val OBJECT: Type = Type of "Object"

   val LIST: GenericPlaceholder = GenericPlaceholder("List", 1)
   val QUEUE: GenericPlaceholder = GenericPlaceholder("Queue", 1)
   val STACK: GenericPlaceholder = GenericPlaceholder("Stack", 1)
   val SET: GenericPlaceholder = GenericPlaceholder("Set", 1)
   val MAP: GenericPlaceholder = GenericPlaceholder("Map", 2)
   val MAYBE: GenericPlaceholder = GenericPlaceholder("Maybe", 1)
   val COLLECTION: GenericPlaceholder = GenericPlaceholder("Collection", 1)

   val DEFINED_GENERICS: List[GenericPlaceholder] = List(LIST, QUEUE, STACK, SET, MAP, MAYBE, COLLECTION)

  def getType(name: String): Option[Type] = name match {
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
    case "any" | "Any" => Option(ANY)
    case _ => None
  }

  def getPlaceholder(name: String): Option[GenericPlaceholder] = name match {
    case "List" | "ArrayList" | "LinkedList" => Option(LIST)
    case "Queue" | "Deque" => Option(QUEUE)
    case "Stack" => Option(STACK)
    case "Set" | "CopyOnWriteArraySet" | "LinkedHashSet" | "HashSet" => Option(SET)
    case "Map" | "HashMap" | "LinkedHashMap" => Option(MAP)
    case "Optional" | "Option" => Option(MAYBE)
    case "Collection" => Option(COLLECTION)
    case _ => None
  }

  def shouldBuildGeneric(name: String): Boolean = DEFINED_GENERICS.exists(_.genericClass == name)
}