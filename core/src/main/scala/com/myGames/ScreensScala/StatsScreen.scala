package com.myGames.ScreensScala

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{ Gdx, Input, InputAdapter, ScreenAdapter }
import com.myGames.PokemonInfoScala.Player
import com.myGames.Screens.MainGame

class StatsScreen(val game: MainGame, val pokemonScreen: PokemonScreen) extends ScreenAdapter {
  var section: String = "Pokemon"

  override def show(): Unit = Gdx.input.setInputProcessor(new InputAdapter() {
    override def keyDown(keyCode: Int): Boolean = {
      keyCode match {
        case Input.Keys.ESCAPE => game.setScreen(pokemonScreen)
        case Input.Keys.NUM_1 => section = "Pokemon"
        case Input.Keys.NUM_2 => section = "Potions"
      }

      true
    }
  })

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    game.batch.begin()
    val xPos = Gdx.graphics.getWidth * .1f
    if (section == "Pokemon") outputPokemon(xPos)
    else outputPotions(xPos)
    game.batch.end()
  }

  private def outputPokemon(xPos: Float): Unit = {
    Player.pokemonBag.foreach { pokemon =>
      game.font.draw(game.batch, s"Name: ${pokemon.name}", xPos, Gdx.graphics.getHeight * .85f)
      game.font.draw(game.batch, s"Type: ${pokemon.pokemonType.toString}", xPos, Gdx.graphics.getHeight * .65f)
      game.font.draw(game.batch, s"Level: ${pokemon.level}", xPos, Gdx.graphics.getHeight * .6f)
      game.font.draw(game.batch, s"Exp: ${pokemon.exp}", xPos, Gdx.graphics.getHeight * .55f)
      game.font.draw(game.batch, s"Moves: ${pokemon.moveSetToString}", xPos, Gdx.graphics.getHeight * .5f)
    }
  }

  private def outputPotions(xPos: Float): Unit = {
    Player.potionsBag.foreach { case (potion, amount) =>
      game.font.draw(game.batch, s"$potion: $amount", xPos, Gdx.graphics.getHeight * .85f)
    }
  }

  override def hide(): Unit = Gdx.input.setInputProcessor(null)
}