package PokemonInfo

import scala.util.Random

object Info {
  lazy val allMoves: Map[String, Move] = Map(
    "Tackle"     -> Move("Tackle", Type.NORMAL, 10),
    "Ember"      -> Move("Ember", Type.FIRE, 20),
    "Bubblebeam" -> Move("Bubblebeam", Type.WATER, 20),
    "Razor Leaf" -> Move("Razor Leaf", Type.GRASS, 20)
  )

  lazy val starterPokemon: Map[String, Pokemon] = Map(
    "Charmander" ->
      Pokemon("Charmander", Type.FIRE, Seq(Some(allMoves("Tackle")), Some(allMoves("Ember")), None, None)),
    "Squirtle"   ->
      Pokemon("Squirtle", Type.WATER, Seq(Some(allMoves("Tackle")), Some(allMoves("Bubblebeam")), None, None)),
    "Bulbasaur"  ->
      Pokemon("Bulbasaur", Type.GRASS, Seq(Some(allMoves("Tackle")), Some(allMoves("Razor Leaf")), None, None))
  )

  lazy val enemyPokemon: List[Pokemon] = List(
    Pokemon("Rattata", Type.GRASS, Seq(Some(allMoves("Tackle")), None, None, None)),
    Pokemon("Bidoof", Type.GRASS, Seq(Some(allMoves("Tackle")), None, None, None)),
    Pokemon("Starly", Type.GRASS, Seq(Some(allMoves("Tackle")), None, None, None))
  )

  lazy val strengths: Map[Type, Set[Type]] = Map(
    Type.NORMAL   -> Set.empty,
    Type.FIRE     -> Set(Type.GRASS, Type.ICE, Type.BUG, Type.STEEL),
    Type.WATER    -> Set(Type.FIRE, Type.GROUND, Type.ROCK),
    Type.GRASS    -> Set(Type.WATER, Type.GROUND, Type.ROCK),
    Type.ELECTRIC -> Set(Type.WATER, Type.FLYING),
    Type.ICE      -> Set(Type.GRASS, Type.GROUND, Type.FLYING, Type.DRAGON),
    Type.FIGHTING -> Set(Type.NORMAL, Type.ICE, Type.ROCK, Type.DARK, Type.STEEL),
    Type.POISON   -> Set(Type.GRASS),
    Type.GROUND   -> Set(Type.FIRE, Type.ELECTRIC, Type.POISON, Type.ROCK, Type.STEEL),
    Type.FLYING   -> Set(Type.GRASS, Type.FIGHTING, Type.BUG),
    Type.PSYCHIC  -> Set(Type.FIGHTING, Type.POISON),
    Type.BUG      -> Set(Type.GRASS, Type.PSYCHIC, Type.DARK),
    Type.ROCK     -> Set(Type.FIRE, Type.ICE, Type.FLYING, Type.BUG),
    Type.GHOST    -> Set(Type.PSYCHIC, Type.GHOST),
    Type.DRAGON   -> Set(Type.DRAGON),
    Type.DARK     -> Set(Type.PSYCHIC, Type.GHOST),
    Type.STEEL    -> Set(Type.ICE, Type.ROCK)
  )

  lazy val weaknesses: Map[Type, Set[Type]] = Map(
    Type.NORMAL   -> Set(Type.ROCK, Type.STEEL),
    Type.FIRE     -> Set(Type.FIRE, Type.WATER, Type.ROCK, Type.DRAGON),
    Type.WATER    -> Set(Type.WATER, Type.GRASS, Type.DRAGON),
    Type.GRASS    -> Set(Type.FIRE, Type.GRASS, Type.POISON, Type.FLYING, Type.BUG, Type.DRAGON, Type.STEEL),
    Type.ELECTRIC -> Set(Type.GRASS, Type.ELECTRIC, Type.DRAGON),
    Type.ICE      -> Set(Type.FIRE, Type.WATER, Type.ICE, Type.STEEL),
    Type.FIGHTING -> Set(Type.POISON, Type.FLYING, Type.PSYCHIC, Type.BUG),
    Type.POISON   -> Set(Type.POISON, Type.GROUND, Type.ROCK, Type.GHOST),
    Type.GROUND   -> Set(Type.GRASS, Type.BUG),
    Type.FLYING   -> Set(Type.ELECTRIC, Type.ROCK, Type.STEEL),
    Type.PSYCHIC  -> Set(Type.PSYCHIC, Type.STEEL),
    Type.BUG      -> Set(Type.FIRE, Type.FIGHTING, Type.POISON, Type.FLYING, Type.GHOST, Type.STEEL),
    Type.ROCK     -> Set(Type.FIGHTING, Type.GROUND, Type.STEEL),
    Type.GHOST    -> Set(Type.DARK),
    Type.DRAGON   -> Set(Type.STEEL),
    Type.DARK     -> Set(Type.FIGHTING, Type.DARK),
    Type.STEEL    -> Set(Type.FIRE, Type.WATER, Type.ELECTRIC, Type.STEEL)
  )


  lazy val noEffect: Map[Type, Set[Type]] = Map(
    Type.NORMAL   -> Set(Type.GHOST),
    Type.FIRE     -> Set.empty,
    Type.WATER    -> Set.empty,
    Type.GRASS    -> Set.empty,
    Type.ELECTRIC -> Set(Type.GROUND),
    Type.ICE      -> Set.empty,
    Type.FIGHTING -> Set(Type.GHOST),
    Type.POISON   -> Set(Type.STEEL),
    Type.GROUND   -> Set(Type.ELECTRIC),
    Type.FLYING   -> Set.empty,
    Type.PSYCHIC  -> Set(Type.DARK),
    Type.BUG      -> Set.empty,
    Type.ROCK     -> Set.empty,
    Type.GHOST    -> Set(Type.NORMAL),
    Type.DRAGON   -> Set.empty,
    Type.DARK     -> Set.empty,
    Type.STEEL    -> Set.empty
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
}