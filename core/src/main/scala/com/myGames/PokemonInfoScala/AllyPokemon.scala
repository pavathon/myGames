package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfoScala.AllyPokemon.maxExp


class AllyPokemon(
  override val name: String,
  override val pokemonType: Type.Value,
  override val moveSet: List[Option[Move]],
  var level: Int = 1,
  var exp: Int = 0,
) extends PokemonTrait {

  def gainExp(gainedExp: Int): Unit = {
    exp += gainedExp
    if (exp >= maxExp) levelUp()
  }

  private def levelUp(): Unit = {
    level += 1
    exp = 0
  }

}

object AllyPokemon {
  private val maxExp: Int = 100
}