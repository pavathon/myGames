package com.myGames.PokemonInfoScala

import scala.collection.mutable.ArrayBuffer

object Player {
  val pokemonBag: ArrayBuffer[AllyPokemon] = ArrayBuffer.empty

  def addPokemon(allyPokemon: AllyPokemon): Unit = {
    pokemonBag += allyPokemon
  }
}
