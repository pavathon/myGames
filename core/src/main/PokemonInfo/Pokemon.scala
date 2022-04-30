package PokemonInfo

case class Pokemon(
  name: String,
  pokemonType: Type,
  moveSet: Seq[Option[Move]]
) extends PokemonTrait


