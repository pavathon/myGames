package com.myGames.Screens

import com.badlogic.gdx.{Gdx, Input, InputAdapter, ScreenAdapter}
import com.badlogic.gdx.graphics.GL20
import com.myGames.PokemonInfoScala.{AllyPokemon, Player}
import com.myGames.Screens.Pokemon.PokemonScreen

class StatsScreen(val game: MainGame, val pokemonScreen: PokemonScreen) extends ScreenAdapter {
  override def show(): Unit = Gdx.input.setInputProcessor(new InputAdapter() {
    override def keyDown(keyCode: Int): Boolean = {
      if (keyCode == Input.Keys.ESCAPE) game.setScreen(pokemonScreen)
      true
    }
  })

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    game.batch.begin()
    Player.pokemonBag.foreach((pokemon: AllyPokemon) => {
      val xPos = Gdx.graphics.getWidth * .1f
      game.font.draw(game.batch, "Name: " + pokemon.name, xPos, Gdx.graphics.getHeight * .85f)
      game.font.draw(game.batch, "Type: " + pokemon.pokemonType.toString, xPos, Gdx.graphics.getHeight * .65f)
      game.font.draw(game.batch, "Level: " + pokemon.level, xPos, Gdx.graphics.getHeight * .6f)
      game.font.draw(game.batch, "Exp: " + pokemon.exp, xPos, Gdx.graphics.getHeight * .55f)
      game.font.draw(game.batch, "Moves: " + pokemon.moveSetToString, xPos, Gdx.graphics.getHeight * .5f)
    })
    game.batch.end()
  }

  override def hide(): Unit = Gdx.input.setInputProcessor(null)
}