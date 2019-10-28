package uml.model.types

case object StandardTypes {

  trait StandardType extends Type {
    override def matchesWith(name: String): Boolean = this.name == name
  }

  case object Int extends StandardType {
    override def name: String = "Int"
  }

  case object Char extends StandardType {
    override def name: String = "Char"
  }

  case object Double extends StandardType {
    override def name: String = "Double"
  }

  case object String extends StandardType {
    override def name: String = "String"
  }

  case object Float extends StandardType {
    override def name: String = "Float"
  }

  case object Bool extends StandardType {
    override def name: String = "Bool"
  }

  case object Long extends StandardType {
    override def name: String = "Long"
  }

  case object Short extends StandardType {
    override def name: String = "Short"
  }

  case object Void extends StandardType {
    override def name: String = "Void"
  }

  case object Byte extends StandardType {
    override def name: String = "Byte"
  }

  case object Any extends StandardType {
    override def name: String = "Any"
  }

  case object Object extends StandardType {
    override def name: String = "Object"
  }

  val LIST: GenericPlaceholder = GenericPlaceholder("List", 1)
  val QUEUE: GenericPlaceholder = GenericPlaceholder("Queue", 1)
  val STACK: GenericPlaceholder = GenericPlaceholder("Stack", 1)
  val SET: GenericPlaceholder = GenericPlaceholder("Set", 1)
  val MAP: GenericPlaceholder = GenericPlaceholder("Map", 2)
  val MAYBE: GenericPlaceholder = GenericPlaceholder("Maybe", 1)
  val COLLECTION: GenericPlaceholder = GenericPlaceholder("Collection", 1)

  def getType(name: String): Option[Type] = name match {
    case "int" | "Integer" => Option(Int)
    case "char" | "Char" => Option(Char)
    case "double" | "Double" => Option(Double)
    case "String" => Option(String)
    case "float" | "Float" => Option(Float)
    case "boolean" | "Boolean" => Option(Bool)
    case "long" | "Long" => Option(Long)
    case "short" | "Short" => Option(Short)
    case "void" | "Void" => Option(Void)
    case "byte" | "Byte" => Option(Byte)
    case "any" | "Any" => Option(Any)
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

  def contains(_type: Type): Boolean = standardTypes.contains(_type)

  private def standardTypes: List[Type] = List(Int, Char, Double, Float, String, Bool, Long, Short, Void, Byte, Any,
    Object)
}