package uml.utils

import uml.exception.NoSuchModifierException
import uml.model.Modifiers
import uml.model.Modifiers.Modifier

object Implicits {

  implicit class RichString(str: String) {
    def toModifier: Modifier = str match {
      case "public" => Modifiers.Public
      case "private" => Modifiers.Private
      case "protected" => Modifiers.Protected
      case "static" => Modifiers.Static
      case "final" => Modifiers.Final
      case "default" => Modifiers.Default
      case "synchronized" => Modifiers.Synchronized
      case "volatile" => Modifiers.Volatile
      case "abstract" => Modifiers.Abstract
      case _ => throw NoSuchModifierException(str)
    }

    def removeByRegex(regex: String): String = str.replaceAll(regex, "")
  }

}