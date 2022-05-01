package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfo.Type

case class Pokemon(
                    name: String,
                    pokemonType: Type,
                    moveSet: Seq[Option[Move]]
                  ) extends PokemonTrait
