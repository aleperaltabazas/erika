package uml.model.types

case object StandardTypes {
  val INT: Type = SimpleType("Int")
  val CHAR: Type = SimpleType("Character")
  val DOUBLE: Type = SimpleType("Double")
  val STRING: Type = SimpleType("String")
  val FLOAT: Type = SimpleType("Float")
  val BOOL: Type = SimpleType("Boolean")
  val LONG: Type = SimpleType("Long")
  val SHORT: Type = SimpleType("Short")
  val VOID: Type = SimpleType("Void")
  val BYTE: Type = SimpleType("Byte")
  val ANY: Type = SimpleType("Any")
  val OBJECT: Type = SimpleType("Object")

  val LIST: GenericPlaceholder = GenericPlaceholder("List", 1)
  val QUEUE: GenericPlaceholder = GenericPlaceholder("Queue", 1)
  val STACK: GenericPlaceholder = GenericPlaceholder("Stack", 1)
  val SET: GenericPlaceholder = GenericPlaceholder("Set", 1)
  val MAP: GenericPlaceholder = GenericPlaceholder("Map", 2)
  val MAYBE: GenericPlaceholder = GenericPlaceholder("Maybe", 1)
  val COLLECTION: GenericPlaceholder = GenericPlaceholder("Collection", 1)

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

  def shouldBuildGeneric(name: String): Boolean = getPlaceholder(name).isDefined
}