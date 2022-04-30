package PokemonInfo

import PokemonInfo.AllyPokemon.maxExp

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
    if (exp >= maxExp) {
      level += 1
      exp -= maxExp
    }
  }
}

object AllyPokemon {
  private val maxExp: Int = 100
}