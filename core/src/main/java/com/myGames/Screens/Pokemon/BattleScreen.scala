package com.myGames.Screens.Pokemon

import com.badlogic.gdx.graphics.g2d.{ BitmapFont, TextureAtlas }
import com.badlogic.gdx.graphics.{ Color, GL20, Texture }
import com.badlogic.gdx.scenes.scene2d.ui.{ Image, Label, ProgressBar, Skin, Table, TextButton }
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.{ Actor, Stage }
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.{ Gdx, ScreenAdapter }
import com.myGames.PokemonInfoScala._
import com.myGames.Screens.MainGame

import scala.collection.mutable.ArrayBuffer

class BattleScreen(val mainGame: MainGame, val pokemonScreen: PokemonScreen) extends ScreenAdapter {
  private val stage: Stage = new Stage()

  override def show(): Unit = {
    createBattle()
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(255, 255, 255, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.draw()
  }

  private def createBattle(): Unit = {
    Gdx.input.setInputProcessor(stage)
    val playerPokemon: ArrayBuffer[AllyPokemon] = Player.pokemonBag
    val enemyPokemon: Pokemon = Info.getRandomEnemyPokemon

    val skin: Skin = createSkin
    val font: BitmapFont = createFont(3f)

    val minWidth: Int = 500
    val minHeight: Int = 100

    val healthBarStyle: ProgressBar.ProgressBarStyle = createHealthBarStyle(skin, minWidth, minHeight)
    val allyHealthBar: ProgressBar = createHealthBar(healthBarStyle, minWidth, minHeight)
    allyHealthBar.setPosition(50, MainGame.WORLD_HEIGHT - 150)
    val enemyHealthBar: ProgressBar = createHealthBar(healthBarStyle, minWidth, minHeight)
    enemyHealthBar.setPosition(MainGame.WORLD_WIDTH - enemyHealthBar.getWidth - 50, MainGame.WORLD_HEIGHT - 150)

    val nameLabelStyle: Label.LabelStyle = createNameLabelStyle(font)
    val allyNameLabel: Label = createNameLabel(playerPokemon.head.name, nameLabelStyle, allyHealthBar)
    val enemyNameLabel: Label = createNameLabel(enemyPokemon.name, nameLabelStyle, enemyHealthBar)

    val allyPokemonImage: Image = createAllyPokemonImage

    val battleDescriptionLabel: Label = createBattleDescriptionLabel

    val moveNames: List[String] = playerPokemon.head.getMoveNames
    val moveButtons: List[TextButton] = createMoveButtons(font, skin, moveNames)
    addMoveButtonListeners(moveButtons, playerPokemon.head, enemyHealthBar, enemyPokemon, battleDescriptionLabel, minWidth)
    val moveButtonsTable: Table = createMoveButtonsTable(moveButtons)
    battleDescriptionLabel.setPosition(moveButtonsTable.getX, moveButtonsTable.getY + moveButtonsTable.getHeight + 10)

    stage.addActor(moveButtonsTable)
    stage.addActor(battleDescriptionLabel)
    stage.addActor(enemyHealthBar)
    stage.addActor(enemyNameLabel)
    stage.addActor(allyHealthBar)
    stage.addActor(allyPokemonImage)
    stage.addActor(allyNameLabel)
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
    stage.clear()
  }

  override def dispose(): Unit = stage.dispose()

  private def createSkin: Skin = {
    val skin = new Skin
    val buttonAtlas = new TextureAtlas("gdx-skins/default/skin/uiskin.atlas")
    skin.addRegions(buttonAtlas)
    skin
  }

  private def createFont(scale: Float): BitmapFont = {
    val font = new BitmapFont(Gdx.files.internal("core/assets/gdx-skins/sgx/skin/font-export.fnt"), Gdx.files.internal("core/assets/gdx-skins/sgx/raw/font-export.png"), false)
    font.getData.setScale(scale)
    font
  }

  private def createHealthBarStyle(skin: Skin, minWidth: Int, minHeight: Int): ProgressBar.ProgressBarStyle = {
    val healthBarStyle = new ProgressBar.ProgressBarStyle
    healthBarStyle.background = skin.getDrawable("default-slider")
    healthBarStyle.disabledBackground = skin.getDrawable("default-slider")
    healthBarStyle.background.setMinWidth(minWidth)
    healthBarStyle.background.setMinHeight(minHeight)
    healthBarStyle.disabledBackground.setMinWidth(minWidth)
    healthBarStyle.disabledBackground.setMinHeight(minHeight)
    healthBarStyle
  }

  private def createHealthBar(healthBarStyle: ProgressBar.ProgressBarStyle, minWidth: Int, minHeight: Int): ProgressBar = {
    val healthBar = new ProgressBar(0, 100, 1f, false, healthBarStyle)
    healthBar.setSize(minWidth, minHeight)
    healthBar.setColor(Color.GREEN)
    healthBar
  }

  private def createNameLabelStyle(font: BitmapFont): Label.LabelStyle = new Label.LabelStyle(font, Color.BLACK)

  private def createNameLabel(pokemonName: String, nameLabelStyle: Label.LabelStyle, healthBar: ProgressBar): Label = {
    val pokemonNameLabel = new Label(pokemonName, nameLabelStyle)
    pokemonNameLabel.setPosition(healthBar.getX, healthBar.getY - healthBar.getHeight)
    pokemonNameLabel
  }

  private def createBattleDescriptionLabel: Label = {
    val font = createFont(2f)
    val labelStyle = new Label.LabelStyle(font, Color.BLACK)
    new Label("It's your turn!", labelStyle)
  }

  private def createAllyPokemonImage: Image = {
    val allyImg = new Image(new Texture(Gdx.files.internal("gigachad.png")))
    allyImg.setPosition(50, 600)
    allyImg.setScale(0.5f)
    allyImg
  }

  private def createMoveButtons(
    font: BitmapFont,
    skin: Skin,
    moveNames: List[String],
  ): List[TextButton] =
    moveNames.map { moveName =>
      val moveButtonStyle = new TextButton.TextButtonStyle
      moveButtonStyle.font = font
      moveButtonStyle.up = skin.getDrawable("default-select")
      moveButtonStyle.down = skin.getDrawable("default-select-selection")
      val moveButton = new TextButton(moveName, moveButtonStyle)
      moveButton
    }


  private def addMoveButtonListeners(
    moveButtons: List[TextButton],
    ally: AllyPokemon,
    enemyHealthBar: ProgressBar,
    enemy: Pokemon,
    battleDescriptionLabel: Label,
    minWidth: Float
  ): Unit = {
    val (emptyMoves, actualMoves) = moveButtons.partition(_.getText == "-")
    emptyMoves.foreach { move =>
      move.getStyle.down = null
      move.setDisabled(true)
    }
    actualMoves.foreach { move =>
      move.addListener(new ChangeListener() {
        override def changed(event: ChangeListener.ChangeEvent, actor: Actor): Unit = {
          val (effectiveDmg, effectiveText) = Info.getEffectiveDamage(move.getText.toString, enemy)
          val dmg = effectiveDmg * (minWidth / 100)
          val remainingHealth = enemyHealthBar.getWidth - dmg
          if (remainingHealth <= 0) {
            enemyHealthBar.setWidth(0)
            moveButtons.foreach(moveButton => {
              moveButton.getStyle.down = null
              moveButton.setDisabled(true)
            })
            battleDescriptionLabel.setText(s"You have defeated the enemy ${enemy.name}! You have gained 50 exp")
            ally.gainExp(50)
            Timer.schedule(new Timer.Task() {
              override def run(): Unit = {
                mainGame.setScreen(pokemonScreen)
                GameSave.saveGame(ally)
              }
            }, 2)
          }
          else {
            enemyHealthBar.setWidth(remainingHealth)
            setColorStatus(enemyHealthBar)
            battleDescriptionLabel.setText(effectiveText)
          }
        }
    })}
  }

  private def createMoveButtonsTable(moveButtons: List[TextButton]): Table = {
    val table = new Table
    val (top, bottom) = moveButtons.splitAt(2)
    top.foreach(table.add(_).expand.fill)
    table.row
    bottom.foreach(table.add(_).expand.fill)
    table.setSize(MainGame.WORLD_WIDTH, MainGame.WORLD_HEIGHT / 3)
    table
  }

  private def setColorStatus(healthBar: ProgressBar): Unit =
    if (healthBar.getWidth <= 90) healthBar.setColor(Color.RED)
    else healthBar.setColor(Color.GREEN)
}