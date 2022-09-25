package com.myGames.ScreensScala

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{ Gdx, Input, InputAdapter, ScreenAdapter }
import com.myGames.PokemonInfoScala.{ GameSave, Player, Potions }
import com.myGames.Screens.MainGame

class ShopScreen(val game: MainGame, val pokemonScreen: PokemonScreen) extends ScreenAdapter {
  val purchasePotionSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/purchasePotion.wav"))

  override def show(): Unit = Gdx.input.setInputProcessor(new InputAdapter() {
    override def keyDown(keyCode: Int): Boolean = {
      keyCode match {
        case Input.Keys.ESCAPE =>
          GameSave.saveGame()
          game.setScreen(pokemonScreen)
        case Input.Keys.NUM_1 =>
          Player.addPotion(Potions.Potion)
          purchasePotionSound.play()
      }
      true
    }
  })

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    game.batch.begin()
    val xPos = Gdx.graphics.getWidth * .1f
    game.font.draw(game.batch, "Welcome to the shop! What would you like to buy?", xPos, Gdx.graphics.getHeight * .85f)
    game.font.draw(game.batch, "1. Potion", xPos, Gdx.graphics.getHeight * .65f)
    game.batch.end()
  }

  override def hide(): Unit = Gdx.input.setInputProcessor(null)

  override def dispose(): Unit = purchasePotionSound.dispose()
}
