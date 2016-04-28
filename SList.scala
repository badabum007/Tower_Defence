package application

import scala.collection.JavaConversions
import scala.collection.mutable.Set
import scala.collection.mutable.Seq
import scala.collection.mutable.MutableList

class SList {
  val tempList: MutableList[Int] = MutableList()

  def addEl(element: Int) {
    tempList.+=(element)
  }

  def ret(): java.util.List[Int] = {
    JavaConversions.mutableSeqAsJavaList(tempList)
  }

  def getElem(indexX: Int, indexY: Int, size: Int){
    val temp: Int = indexX * size + indexY
    val k: Option[Int] = tempList.get(temp)
  }
}