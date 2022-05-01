package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfo.Type

case class Move(name: String, moveType: Type, damage: Int) {

  override def toString: String = {
    s"($name, ${moveType.toString}, $damage)"
  }
}
