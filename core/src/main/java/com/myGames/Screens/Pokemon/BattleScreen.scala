package com.myGames.Screens.Pokemon

import com.badlogic.gdx.graphics.g2d.{ BitmapFont, TextureAtlas }
import com.badlogic.gdx.graphics.{ Color, GL20, Texture }
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.{ Actor, Stage }
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.{ Gdx, ScreenAdapter }
import com.myGames.PokemonInfoScala._
import com.myGames.Screens.MainGame

import java.util
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

    val moveNames: util.List[String] = playerPokemon.head.getMoveNames
    val moveButtons: Array[TextButton] = createMoveButtons(font, skin, moveNames, playerPokemon.head, enemyHealthBar, enemyPokemon, battleDescriptionLabel, minWidth)
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

  private def createMoveButtons(font: BitmapFont, skin: Skin, moveNames: util.List[String], ally: AllyPokemon, enemyHealthBar: ProgressBar, enemy: Pokemon, battleDescriptionLabel: Label, minWidth: Float): Array[TextButton] = {
    val moveButtons = new Array[TextButton](4)
    for (index <- 0 until 4) {
      val moveButtonStyle = new TextButton.TextButtonStyle
      moveButtonStyle.font = font
      moveButtonStyle.up = skin.getDrawable("default-select")
      val moveButton = new TextButton(moveNames.get(index), moveButtonStyle)
      if (!(moveNames.get(index) == "-")) {
        moveButtonStyle.down = skin.getDrawable("default-select-selection")
        moveButton.addListener(addMoveButtonListener(moveNames.get(index), ally, enemyHealthBar, enemy, battleDescriptionLabel, minWidth))
      }
      else moveButton.setDisabled(true)
      moveButtons(index) = moveButton
    }
    moveButtons
  }

  private def addMoveButtonListener(attackMove: String, ally: AllyPokemon, enemyHealthBar: ProgressBar, enemy: Pokemon, battleDescriptionLabel: Label, minWidth: Float): ChangeListener = new ChangeListener() {
    override def changed(event: ChangeListener.ChangeEvent, actor: Actor): Unit = {
      val effective = Info.getEffectiveDamage(attackMove, enemy)
      val dmg = effective._1 * (minWidth / 100)
      val remainingHealth = enemyHealthBar.getWidth - dmg
      if (remainingHealth <= 0) {
        enemyHealthBar.setWidth(0)
        battleDescriptionLabel.setText("You have defeated the enemy " + enemy.name + "!" + " You have gained 50 exp")
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
        battleDescriptionLabel.setText(effective._2)
      }
    }
  }

  private def createMoveButtonsTable(moveButtons: Array[TextButton]): Table = {
    val table = new Table
    table.add(moveButtons(0)).expand.fill
    table.add(moveButtons(1)).expand.fill
    table.row
    table.add(moveButtons(2)).expand.fill
    table.add(moveButtons(3)).expand.fill
    table.setSize(MainGame.WORLD_WIDTH, MainGame.WORLD_HEIGHT / 3)
    table
  }

  private def setColorStatus(healthBar: ProgressBar): Unit =
    if (healthBar.getWidth <= 90) healthBar.setColor(Color.RED)
    else healthBar.setColor(Color.GREEN)
}