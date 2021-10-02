package PokemonInfo

case class Move(name: String, moveType: Type, damage: Int) {

  override def toString: String = {
    val moveTypeStr = moveType.toString
    s"($name, $moveTypeStr, $damage)"
  }

}
