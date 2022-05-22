package com.myGames.PokemonInfoScala

case class Pokemon(
  name: String,
  pokemonType: Type.Value,
  moveSet: Seq[Option[Move]]
) extends PokemonTrait
