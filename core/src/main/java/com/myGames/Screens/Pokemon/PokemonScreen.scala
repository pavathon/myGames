package com.myGames.Screens.Pokemon

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{ Gdx, Input, InputAdapter, ScreenAdapter }
import com.myGames.PokemonInfoScala.{ Info, Player, SaveGame }
import com.myGames.Screens.MainGame
import com.myGames.ScreensScala.StatsScreen

class PokemonScreen() extends ScreenAdapter {
  private var game: MainGame = null
  private lazy val battleScreen: BattleScreen = new BattleScreen(game, this)
  private lazy val statsScreen: StatsScreen = new StatsScreen(game, this)

  def this(game: MainGame) {
    this()
    this.game = game
    val ally = SaveGame.loadAllyPokemon
    Player.addAllPokemon(ally)
  }

  def this(game: MainGame, starterPokemonName: String) {
    this()
    this.game = game
    Player.addPokemon(Info.getStarterPokemon(starterPokemonName))
  }

  override def show(): Unit = Gdx.input.setInputProcessor(new InputAdapter() {
    override def keyDown(keyCode: Int): Boolean = {
      keyCode match {
        case Input.Keys.ESCAPE => game.setScreen(game.menuScreen)
        case Input.Keys.NUM_1 => game.setScreen(battleScreen)
        case Input.Keys.NUM_2 => game.setScreen(statsScreen)
      }
      true
    }
  })

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    game.batch.begin()
    game.font.draw(game.batch, "Welcome to Pokemon!", Gdx.graphics.getWidth * .4f, Gdx.graphics.getHeight * .85f)
    game.font.draw(game.batch, "Press 1 to battle a pokemon.", Gdx.graphics.getWidth * .25f, Gdx.graphics.getHeight * .65f)
    game.font.draw(game.batch, "Press 2 to look at your pokemon", Gdx.graphics.getWidth * .25f, Gdx.graphics.getHeight * .6f)
    game.batch.end()
  }

  override def hide(): Unit = Gdx.input.setInputProcessor(null)
}