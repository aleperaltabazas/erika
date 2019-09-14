package uml.model.methods

import uml.model.types.Type

case class Argument(name: String, argumentType: Type) {

  def write: String = s"$name: ${argumentType.name}"

}
