package uml.utils

import uml.exception.NoSuchModifierException
import uml.model.Modifiers
import uml.model.Modifiers.Modifier
import uml.model.lang.Lang
import uml.model.lang.Lang.Language

object Implicits {

  implicit class RichString(str: String) {
    def toModifier(language: Language): Modifier = language match {
      case Lang.Java => str match {
        case "public" => Modifiers.Public
        case "private" => Modifiers.Private
        case "protected" => Modifiers.Protected
        case "static" => Modifiers.Static
        case "final" => Modifiers.Final
        case "default" => Modifiers.Default
        case "synchronized" => Modifiers.Synchronized
        case "volatile" => Modifiers.Volatile
        case "abstract" => Modifiers.Abstract
        case _ if str.matches("<.*>") => Modifiers.Generic
        case _ => throw NoSuchModifierException(str)
      }
      case Lang.Scala => str match {
        case "private" => Modifiers.Private
        case "protected" => Modifiers.Protected
        case "lazy" => Modifiers.Lazy
        case "final" => Modifiers.Final
        case "override" => Modifiers.Override
        case "abstract" => Modifiers.Abstract
        case "case" => Modifiers.Case
        case "implicit" => Modifiers.Implicit
        case _ => throw NoSuchModifierException(str)
      }
    }

    def removeByRegex(regex: String): String = str.replaceAll(regex, "")

    def splitBy(regex: String): List[String] = str.split(regex).toList

    def words: List[String] = str.trim.split("\\s").toList
  }

  implicit class RichList[T](list: List[T]) {
    def initOrNil: List[T] = {
      list match {
        case Nil => Nil
        case xs :+ _ => xs
      }
    }
  }

}