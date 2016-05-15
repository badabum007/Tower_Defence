package application;

import scala.collection.mutable.Set
import scala.collection.mutable.Seq
import scala.collection.mutable.MutableList
import scala.collection.mutable.ArrayBuffer

object Notation {
  def parseNotation(obj: Any): String = {
    obj match {
      case list: java.util.ArrayList[Int] => output(list)
      case string: String => string
    }
  }

  def output(list: java.util.ArrayList[Int]): String = {
    val k = "TOWER Coordinate X: " + (list.get(0) / Main.BLOCK_SIZE).toString() +
      " Coordinate Y: " + (list.get(1) / Main.BLOCK_SIZE).toString()
    k
  }
}