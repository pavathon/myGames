package com.myGames.ScreensScala

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
  private val stage: Stage = new Stage

  private lazy val skin = createSkin

  private lazy val endBattle = (ally: AllyPokemon) => new Timer.Task {
    override def run(): Unit = {
      mainGame.setScreen(pokemonScreen)
      GameSave.saveGame(ally)
    }
  }

  private lazy val allyTurn = (battleDescriptionLabel: Label, pokemonName: String, moveButtons: List[TextButton]) => new Timer.Task {
    override def run(): Unit =
      battleDescriptionLabel.setText(s"What will $pokemonName do?")
      toggleMoveButtons(moveButtons, isDisabled = false)
  }

  private lazy val enemyTurn = (battleDescriptionLabel: Label) => new Timer.Task {
    override def run(): Unit =
      battleDescriptionLabel.setText("It's the enemy turn!")
  }

  private lazy val enemyAttack = (
    enemy: Pokemon,
    ally: AllyPokemon,
    allyHealthBar: ProgressBar,
    battleDescriptionLabel: Label,
    moveButtons: List[TextButton]
  ) => new Timer.Task {
    override def run(): Unit = {
      val attackMove = enemy.attack
      val (remainingHealth, effectiveText) = attackOpponent(enemy.name, attackMove.name, ally, allyHealthBar)
      if (remainingHealth <= 0) {
        allyHealthBar.setWidth(0)
        battleDescriptionLabel.setText("You died!")
        Timer.schedule(endBattle(ally), 2)
      }
      else {
        allyHealthBar.setWidth(remainingHealth)
        setColorStatus(allyHealthBar)
        battleDescriptionLabel.setText(effectiveText)
        Timer.schedule(allyTurn(battleDescriptionLabel, ally.name, moveButtons), 2)
      }
    }
  }

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

    val battleDescriptionLabel: Label = createBattleDescriptionLabel(playerPokemon.head.name)

    val moveNames: List[String] = playerPokemon.head.getMoveNames
    val moveButtons: List[TextButton] = createMoveButtons(font, moveNames)
    addMoveButtonListeners(moveButtons, allyHealthBar, playerPokemon.head, enemyHealthBar, enemyPokemon, battleDescriptionLabel)
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

  private def createBattleDescriptionLabel(pokemonName: String): Label = {
    val font = createFont(2f)
    val labelStyle = new Label.LabelStyle(font, Color.BLACK)
    new Label(s"What will $pokemonName do?", labelStyle)
  }

  private def createAllyPokemonImage: Image = {
    val allyImg = new Image(new Texture(Gdx.files.internal("gigachad.png")))
    allyImg.setPosition(50, 600)
    allyImg.setScale(0.5f)
    allyImg
  }

  private def createMoveButtons(
    font: BitmapFont,
    moveNames: List[String]
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
    allyHealthbar: ProgressBar,
    ally: AllyPokemon,
    enemyHealthBar: ProgressBar,
    enemy: Pokemon,
    battleDescriptionLabel: Label,
  ): Unit = {
    val (emptyMoves, actualMoves) = moveButtons.partition(_.getText.toString == "-")
    emptyMoves.foreach { move =>
      move.getStyle.down = null
      move.setDisabled(true)
    }
    actualMoves.foreach { move =>
      move.addListener(new ChangeListener {
        override def changed(event: ChangeListener.ChangeEvent, actor: Actor): Unit = {
          val (remainingHealth, effectiveText) = attackOpponent(ally.name, move.getText.toString, enemy, enemyHealthBar)
          if (remainingHealth <= 0) {
            enemyHealthBar.setWidth(0)
            toggleMoveButtons(moveButtons, isDisabled = true)
            battleDescriptionLabel.setText(s"You have defeated the enemy ${enemy.name}! You have gained 50 exp")
            ally.gainExp(50)
            Timer.schedule(endBattle(ally), 2)
          }
          else {
            enemyHealthBar.setWidth(remainingHealth)
            setColorStatus(enemyHealthBar)
            battleDescriptionLabel.setText(effectiveText)
            Timer.schedule(enemyTurn(battleDescriptionLabel), 1.5f)
            toggleMoveButtons(moveButtons, isDisabled = true)
            Timer.schedule(enemyAttack(enemy, ally, allyHealthbar, battleDescriptionLabel, moveButtons), 3)
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

  private def attackOpponent[P <: PokemonTrait](pokemonName: String, moveName: String, opponent: P, opponentHealthBar: ProgressBar): (Float, String) = {
    val (effectiveDmg, effectiveText) = Info.getEffectiveDamage(pokemonName, moveName, opponent)
    val dmg = effectiveDmg * (opponentHealthBar.getStyle.background.getMinWidth / 100)
    (opponentHealthBar.getWidth - dmg, effectiveText)
  }

  private def toggleMoveButtons(moveButtons: List[TextButton], isDisabled: Boolean): Unit = {
    moveButtons.filterNot(_.getText.toString == "-").foreach { moveButton =>
      if (isDisabled) moveButton.getStyle.down = null
      else moveButton.getStyle.down = skin.getDrawable("default-select-selection")
      moveButton.setDisabled(isDisabled)
    }
  }

  private def setColorStatus(healthBar: ProgressBar): Unit =
    if (healthBar.getWidth <= 90) healthBar.setColor(Color.RED)
    else healthBar.setColor(Color.GREEN)
}