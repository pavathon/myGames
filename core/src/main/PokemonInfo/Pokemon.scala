package PokemonInfo

case class Pokemon(name: String, moveType: Type, moveSet: Seq[Option[Move]]) {
  def moveSetToString: String = {
    moveSet.flatten.mkString(" ")
  }
}
