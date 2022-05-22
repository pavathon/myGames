package com.myGames.PokemonInfoScala

import scala.util.Random

object Info {
  lazy val allMoves: Map[String, Move] = Map(
    "Tackle" -> Move("Tackle", Type.Normal, 10),
    "Ember" -> Move("Ember", Type.Fire, 20),
    "Bubblebeam" -> Move("Bubblebeam", Type.Water, 20),
    "Razor Leaf" -> Move("Razor Leaf", Type.Grass, 20)
  )

  lazy val starterPokemon: Map[String, AllyPokemon] = Map(
    "Charmander" ->
      new AllyPokemon("Charmander", Type.Fire, List(Some(allMoves("Tackle")), Some(allMoves("Ember")), None, None)),
    "Squirtle" ->
      new AllyPokemon("Squirtle", Type.Water, List(Some(allMoves("Tackle")), Some(allMoves("Bubblebeam")), None, None)),
    "Bulbasaur" ->
      new AllyPokemon("Bulbasaur", Type.Grass, List(Some(allMoves("Tackle")), Some(allMoves("Razor Leaf")), None, None))
  )

  lazy val enemyPokemon: List[Pokemon] = List(
    Pokemon("Rattata", Type.Normal, Seq(Some(allMoves("Tackle")), None, None, None)),
    Pokemon("Budew", Type.Grass, Seq(Some(allMoves("Tackle")), None, None, None)),
    Pokemon("Starly", Type.Flying, Seq(Some(allMoves("Tackle")), None, None, None))
  )

  lazy val strengths: Map[Type.Value, Set[Type.Value]] = Map(
    Type.Normal -> Set.empty,
    Type.Fire -> Set(Type.Grass, Type.Ice, Type.Bug, Type.Steel),
    Type.Water -> Set(Type.Fire, Type.Ground, Type.Rock),
    Type.Grass -> Set(Type.Water, Type.Ground, Type.Rock),
    Type.Electric -> Set(Type.Water, Type.Flying),
    Type.Ice -> Set(Type.Grass, Type.Ground, Type.Flying, Type.Dragon),
    Type.Fighting -> Set(Type.Normal, Type.Ice, Type.Rock, Type.Dark, Type.Steel),
    Type.Poison -> Set(Type.Grass),
    Type.Ground -> Set(Type.Fire, Type.Electric, Type.Poison, Type.Rock, Type.Steel),
    Type.Flying -> Set(Type.Grass, Type.Fighting, Type.Bug),
    Type.Psychic -> Set(Type.Fighting, Type.Poison),
    Type.Bug -> Set(Type.Grass, Type.Psychic, Type.Dark),
    Type.Rock -> Set(Type.Fire, Type.Ice, Type.Flying, Type.Bug),
    Type.Ghost -> Set(Type.Psychic, Type.Ghost),
    Type.Dragon -> Set(Type.Dragon),
    Type.Dark -> Set(Type.Psychic, Type.Ghost),
    Type.Steel -> Set(Type.Ice, Type.Rock)
  )

  lazy val weaknesses: Map[Type.Value, Set[Type.Value]] = Map(
    Type.Normal -> Set(Type.Rock, Type.Steel),
    Type.Fire -> Set(Type.Fire, Type.Water, Type.Rock, Type.Dragon),
    Type.Water -> Set(Type.Water, Type.Grass, Type.Dragon),
    Type.Grass -> Set(Type.Fire, Type.Grass, Type.Poison, Type.Flying, Type.Bug, Type.Dragon, Type.Steel),
    Type.Electric -> Set(Type.Grass, Type.Electric, Type.Dragon),
    Type.Ice -> Set(Type.Fire, Type.Water, Type.Ice, Type.Steel),
    Type.Fighting -> Set(Type.Poison, Type.Flying, Type.Psychic, Type.Bug),
    Type.Poison -> Set(Type.Poison, Type.Ground, Type.Rock, Type.Ghost),
    Type.Ground -> Set(Type.Grass, Type.Bug),
    Type.Flying -> Set(Type.Electric, Type.Rock, Type.Steel),
    Type.Psychic -> Set(Type.Psychic, Type.Steel),
    Type.Bug -> Set(Type.Fire, Type.Fighting, Type.Poison, Type.Flying, Type.Ghost, Type.Steel),
    Type.Rock -> Set(Type.Fighting, Type.Ground, Type.Steel),
    Type.Ghost -> Set(Type.Dark),
    Type.Dragon -> Set(Type.Steel),
    Type.Dark -> Set(Type.Fighting, Type.Dark),
    Type.Steel -> Set(Type.Fire, Type.Water, Type.Electric, Type.Steel)
  )


  lazy val noEffect: Map[Type.Value, Set[Type.Value]] = Map(
    Type.Normal -> Set(Type.Ghost),
    Type.Fire -> Set.empty,
    Type.Water -> Set.empty,
    Type.Grass -> Set.empty,
    Type.Electric -> Set(Type.Ground),
    Type.Ice -> Set.empty,
    Type.Fighting -> Set(Type.Ghost),
    Type.Poison -> Set(Type.Steel),
    Type.Ground -> Set(Type.Electric),
    Type.Flying -> Set.empty,
    Type.Psychic -> Set(Type.Dark),
    Type.Bug -> Set.empty,
    Type.Rock -> Set.empty,
    Type.Ghost -> Set(Type.Normal),
    Type.Dragon -> Set.empty,
    Type.Dark -> Set.empty,
    Type.Steel -> Set.empty
  )

  def getEffectiveDamage(attackerMoveName: String, defender: Pokemon): (Float, String) = {
    val attackerMove: Move = allMoves(attackerMoveName)

    val effectiveness: Float =
      if (strengths(attackerMove.moveType).contains(defender.pokemonType)) 2.0f
      else if (weaknesses(attackerMove.moveType).contains(defender.pokemonType)) 0.5f
      else if (noEffect(attackerMove.moveType).contains(defender.pokemonType)) 0f
      else 1f

    (attackerMove.damage * effectiveness, s"You used $attackerMoveName! ${getEffectiveString(effectiveness)}")
  }

  private def getEffectiveString(effectiveness: Float): String = {
    effectiveness match {
      case 2.0f => s"It's super effective!"
      case 1f => s"It's normally effective."
      case 0.5f => s"It's not very effective."
      case 0f => s"It did nothing."
    }
  }

  def getRandomEnemyPokemon: Pokemon = {
    val index: Int = Random.nextInt(enemyPokemon.length)
    enemyPokemon(index)
  }

  def getStarterPokemon(pokemonName: String): AllyPokemon = {
    starterPokemon(pokemonName)
  }

}
