package uml.repository

abstract class AbstractRepository[T](private var elements: List[T] = List()) {
  def addAll(elements: List[T]): Unit = this.elements = elements

  def findAll(condition: T => Boolean): List[T] = elements.filter(condition)

  def find(condition: T => Boolean): Option[T] = elements.find(condition)

  def removeIf(condition: T => Boolean): Unit = elements = elements.filterNot(condition)

  def add(t: T): Unit = elements = t :: elements

  def getAll: List[T] = elements

  def foreach(callback: T => Unit): Unit = elements.foreach(callback)

  def isEmpty: Boolean = elements.isEmpty

  def head: T = elements.head
}