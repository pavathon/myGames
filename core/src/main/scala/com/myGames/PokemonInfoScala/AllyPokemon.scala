package com.myGames.PokemonInfoScala


class AllyPokemon(
  givenName: String,
  givenPokemonType: Type.Value,
  givenMoveSet: List[Option[Move]]
) extends PokemonTrait {
  override def name: String = givenName
  override def pokemonType: Type.Value = givenPokemonType
  override def moveSet: List[Option[Move]] = givenMoveSet

  var level: Int = 1
  var exp: Int = 0

  def this(
    givenName: String,
    givenPokemonType: Type.Value,
    givenMoveSet: List[Option[Move]],
    givenLevel: Int,
    givenExp: Int
  ) = {
    this(givenName, givenPokemonType, givenMoveSet)
    level = givenLevel
    exp = givenExp
  }

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