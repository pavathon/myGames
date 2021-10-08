package PokemonInfo

import java.util
import scala.collection.JavaConverters.seqAsJavaListConverter

case class Pokemon(name: String, moveType: Type, moveSet: Seq[Option[Move]]) {
  def moveSetToString: String = {
    moveSet.flatten.mkString(" ")
  }

  def getMoveNames: util.List[String] = {
    moveSet.map(_.fold("-")(_.name)).asJava
  }
}
