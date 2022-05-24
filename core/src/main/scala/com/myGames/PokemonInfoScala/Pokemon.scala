package com.myGames.PokemonInfoScala

case class Pokemon(
  name: String,
  pokemonType: Type.Value,
  moveSet: List[Option[Move]]
) extends PokemonTrait
