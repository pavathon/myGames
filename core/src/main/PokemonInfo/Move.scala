package PokemonInfo

case class Move(name: String, moveType: Type, damage: Int) {

  override def toString: String = {
    s"($name, ${moveType.toString}, $damage)"
  }
}
