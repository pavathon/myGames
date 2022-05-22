package com.myGames.PokemonInfoScala

case class Move(name: String, moveType: Type.Value, damage: Int) {

  override def toString: String = {
    s"($name, ${moveType.toString}, $damage)"
  }
}
