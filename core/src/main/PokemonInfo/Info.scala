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
      Pokemon("Charmander", Type.FIRE, Seq(Some(allMoves("Tackle")), Some(allMoves("Ember")), None, None)),
    "Squirtle" ->
      Pokemon("Squirtle", Type.WATER, Seq(Some(allMoves("Tackle")), Some(allMoves("Bubblebeam")), None, None)),
    "Bulbasaur" ->
      Pokemon("Bulbasaur", Type.GRASS, Seq(Some(allMoves("Tackle")), Some(allMoves("Razor Leaf")), None, None))
  )
}
