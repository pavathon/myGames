package PokemonInfo

case object Info {
  val allMoves: Map[String, Move] = Map(
    "Tackle" -> Move("Tackle", Type.NORMAL, 10),
    "Ember" -> Move("Ember", Type.FIRE, 20),
    "Bubblebeam" -> Move("Bubblebeam", Type.WATER, 20),
    "Razor Leaf" -> Move("Razor Leaf", Type.GRASS, 20)
  )

  val starterPokemon: Map[String, Pokemon] = Map(
    "Charmander" ->
      Pokemon("Charmander", Type.FIRE, Seq(Some(allMoves("Ember")), Some(allMoves("Tackle")), None, None)),
    "Squirtle" ->
      Pokemon("Squirtle", Type.WATER, Seq(Some(allMoves("Bubblebeam")), Some(allMoves("Tackle")), None, None)),
    "Bulbasaur" ->
      Pokemon("Bulbasaur", Type.GRASS, Seq(Some(allMoves("Razor Leaf")), Some(allMoves("Tackle")), None, None))
  )
}
