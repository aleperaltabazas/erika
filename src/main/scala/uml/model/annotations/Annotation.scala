package uml.model.annotations

case class Annotation(name: String, properties: Map[String, String]) {
  def write: String = s"@$name($formatProperties)"

  private def formatProperties = s"${
    properties.toList.map { prop =>
      s"${prop._1} = ${prop._2}"
    }.mkString(",")
  }"
}