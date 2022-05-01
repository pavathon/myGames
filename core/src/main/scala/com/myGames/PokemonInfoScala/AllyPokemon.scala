package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfo.Type

class AllyPokemon(
  givenName: String,
  givenPokemonType: Type,
  givenMoveSet: Seq[Option[Move]]
) extends PokemonTrait {
  override def name: String = givenName
  override def pokemonType: Type = givenPokemonType
  override def moveSet: Seq[Option[Move]] = givenMoveSet

  var level: Int = 1
  var exp: Int = 0

  def gainExp(gainedExp: Int): Unit = {
    exp += gainedExp
    checkLevelUp()
  }

  def checkLevelUp(): Unit = {
    if (exp >= AllyPokemon.maxExp) {
      level += 1
      exp -= AllyPokemon.maxExp
    }
  }
}

object AllyPokemon {
  private val maxExp: Int = 100
}