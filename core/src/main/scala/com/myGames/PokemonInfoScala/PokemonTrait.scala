package com.myGames.PokemonInfoScala

trait PokemonTrait {
  def name: String

  def pokemonType: Type.Value

  def moveSet: List[Option[Move]]

  def moveSetToString: String = {
    moveSet.flatten.mkString(" ")
  }

  def getMoveNames: List[String] = {
    moveSet.map(_.fold("-")(_.name))
  }
}
