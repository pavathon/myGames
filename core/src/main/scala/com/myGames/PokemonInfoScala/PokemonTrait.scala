package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfo.Type

import java.util
import scala.collection.JavaConverters.seqAsJavaListConverter

trait PokemonTrait {
  def name: String

  def pokemonType: Type

  def moveSet: Seq[Option[Move]]

  def moveSetToString: String = {
    moveSet.flatten.mkString(" ")
  }

  def getMoveNames: util.List[String] = {
    moveSet.map(_.fold("-")(_.name)).asJava
  }
}
