package com.myGames.PokemonInfoScala

import scala.util.Random

case class Pokemon(
  name: String,
  pokemonType: Type.Value,
  moveSet: List[Option[Move]]
) extends PokemonTrait {

  def attack: Move = {
    val random = new Random
    val actualMoves = moveSet.flatten
    actualMoves(random.nextInt(actualMoves.length))
  }
}
