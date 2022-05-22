package com.myGames.PokemonInfoScala

import scala.collection.mutable.ArrayBuffer

object Player {
  val pokemonBag: ArrayBuffer[AllyPokemon] = ArrayBuffer.empty

  def addPokemon(allyPokemon: AllyPokemon): Unit = {
    pokemonBag += allyPokemon
  }

  def addAllPokemon(allyPokemon: List[AllyPokemon]): Unit = {
    pokemonBag.appendAll(allyPokemon)
  }
}
