package application

import scala.collection.JavaConversions
import scala.collection.mutable.Set
import scala.collection.mutable.Seq
import scala.collection.mutable.MutableList
import scala.collection.mutable.ArrayBuffer

class SList {
  val tempList: MutableList[Int] = MutableList()
  val tempBuf: ArrayBuffer[Int] = ArrayBuffer()

  def addEl(element: Int) {
    tempList.+=(element)
    tempBuf += (element)
  }

  def ret(): java.util.List[Int] = {
    val tempArray : MutableList[Int] = for (temp <- tempList) yield temp
    JavaConversions.mutableSeqAsJavaList(tempArray)
  }

  def getElem(indexX: Int, indexY: Int, size: Int): Int ={
    val temp: Int = indexX * size + indexY
    val k = tempList.get(temp)
    val f : Int = tempBuf(temp)
    f
  }
  
  def getMax() : Int = {
    val k = tempBuf.max
    k
  }
}