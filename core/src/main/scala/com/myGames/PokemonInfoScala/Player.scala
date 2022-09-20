package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfoScala.Potions.Potions

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Player {
  val pokemonBag: ArrayBuffer[AllyPokemon] = ArrayBuffer.empty
  val potionsBag: mutable.Map[Potions, Int] = mutable.Map.empty

  def addPokemon(allyPokemon: AllyPokemon): Unit = {
    pokemonBag += allyPokemon
  }

  def addAllPokemon(allyPokemon: List[AllyPokemon]): Unit = {
    pokemonBag.appendAll(allyPokemon)
  }

  def addPotion(potion: Potions): Unit = {
    if (potionsBag.contains(potion)) potionsBag += potion -> (potionsBag(potion) + 1)
    else potionsBag += potion -> 1
  }

  def usedPotion(potion: Potions): Unit = {
    if (potionsBag.contains(potion)) {
      val usedValue = potionsBag(potion) - 1
      if (usedValue <= 0) potionsBag -= potion
      else potionsBag += potion -> (potionsBag(potion) - 1)
    }
    else {
      throw new Exception("Potion not found in potion bag.")
    }
  }
}
