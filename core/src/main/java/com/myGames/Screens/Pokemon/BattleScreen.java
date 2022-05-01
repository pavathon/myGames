package com.myGames.Screens.Pokemon;

import com.myGames.PokemonInfoScala.AllyPokemon;
import com.myGames.PokemonInfoScala.Info;
import com.myGames.PokemonInfo.Player;
import com.myGames.PokemonInfoScala.Pokemon;
import com.myGames.Screens.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class BattleScreen extends ScreenAdapter {
    private final MainGame mainGame;
    private final PokemonScreen pokemonScreen;

    private Stage stage;

    public BattleScreen(MainGame game, PokemonScreen pokemonScreen) {
        this.mainGame = game;
        this.pokemonScreen = pokemonScreen;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) mainGame.setScreen(pokemonScreen);
                return true;
            }
        });

        createBattle();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    private void createBattle() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        ArrayList<AllyPokemon> playerPokemon = Player.getPlayerInstance().getPokemon();
        Pokemon enemyPokemon = Info.getRandomEnemyPokemon();

        Skin skin = createSkin();
        BitmapFont font = createFont(3f);

        int minWidth = 500;
        int minHeight = 100;

        ProgressBar.ProgressBarStyle healthBarStyle = createHealthBarStyle(skin, minWidth, minHeight);
        ProgressBar allyHealthBar = createHealthBar(healthBarStyle, minWidth, minHeight);
        allyHealthBar.setPosition(50, MainGame.WORLD_HEIGHT - 150);
        ProgressBar enemyHealthBar = createHealthBar(healthBarStyle, minWidth, minHeight);
        enemyHealthBar.setPosition(MainGame.WORLD_WIDTH - enemyHealthBar.getWidth() - 50, MainGame.WORLD_HEIGHT - 150);

        Label.LabelStyle nameLabelStyle = createNameLabelStyle(font);
        Label allyNameLabel = createNameLabel(playerPokemon.get(0).name(), nameLabelStyle, allyHealthBar);
        Label enemyNameLabel = createNameLabel(enemyPokemon.name(), nameLabelStyle, enemyHealthBar);

        Image allyPokemonImage = createAllyPokemonImage();

        Label battleDescriptionLabel = createBattleDescriptionLabel();

        List<String> moveNames = playerPokemon.get(0).getMoveNames();

        TextButton[] moveButtons =
            createMoveButtons(font, skin, moveNames, playerPokemon.get(0), enemyHealthBar, enemyPokemon, battleDescriptionLabel, minWidth);

        Table moveButtonsTable = createMoveButtonsTable(moveButtons);

        battleDescriptionLabel.setPosition(moveButtonsTable.getX(), moveButtonsTable.getY() + moveButtonsTable.getHeight() + 10);

        stage.addActor(moveButtonsTable);
        stage.addActor(battleDescriptionLabel);
        stage.addActor(enemyHealthBar);
        stage.addActor(enemyNameLabel);
        stage.addActor(allyHealthBar);
        stage.addActor(allyPokemonImage);
        stage.addActor(allyNameLabel);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Skin createSkin() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas("gdx-skins/default/skin/uiskin.atlas");
        skin.addRegions(buttonAtlas);
        return skin;
    }

    private BitmapFont createFont(float scale) {
        BitmapFont font = new BitmapFont(Gdx.files.internal("core/assets/gdx-skins/sgx/skin/font-export.fnt"),
                Gdx.files.internal("core/assets/gdx-skins/sgx/raw/font-export.png"), false);
        font.getData().setScale(scale);
        return font;
    }

    private ProgressBar.ProgressBarStyle createHealthBarStyle(Skin skin, int minWidth, int minHeight) {
        ProgressBar.ProgressBarStyle healthBarStyle = new ProgressBar.ProgressBarStyle();
        healthBarStyle.background = skin.getDrawable("default-slider");
        healthBarStyle.disabledBackground = skin.getDrawable("default-slider");
        healthBarStyle.background.setMinWidth(minWidth);
        healthBarStyle.background.setMinHeight(minHeight);
        healthBarStyle.disabledBackground.setMinWidth(minWidth);
        healthBarStyle.disabledBackground.setMinHeight(minHeight);
        return healthBarStyle;
    }

    private ProgressBar createHealthBar(
            ProgressBar.ProgressBarStyle healthBarStyle,
            int minWidth,
            int minHeight
    ) {
        ProgressBar healthBar = new ProgressBar(0, 100, 1f, false, healthBarStyle);
        healthBar.setSize(minWidth, minHeight);
        healthBar.setColor(Color.GREEN);
        return healthBar;
    }

    private Label.LabelStyle createNameLabelStyle(BitmapFont font) {
        return new Label.LabelStyle(font, Color.BLACK);
    }

    private Label createNameLabel(String pokemonName, Label.LabelStyle nameLabelStyle, ProgressBar healthBar) {
        Label pokemonNameLabel = new Label(pokemonName, nameLabelStyle);
        pokemonNameLabel.setPosition(healthBar.getX(), healthBar.getY() - healthBar.getHeight());
        return pokemonNameLabel;
    }

    private Label createBattleDescriptionLabel() {
        BitmapFont font = createFont(2f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new Label("It's your turn!",labelStyle);
    }

    private Image createAllyPokemonImage() {
        Image allyImg = new Image(new Texture(Gdx.files.internal("gigachad.png")));
        allyImg.setPosition(50, 600);
        allyImg.setScale(0.5f);
        return allyImg;
    }

    private TextButton[] createMoveButtons(
            BitmapFont font,
            Skin skin,
            List<String> moveNames,
            AllyPokemon ally,
            ProgressBar enemyHealthBar,
            Pokemon enemy,
            Label battleDescriptionLabel,
            float minWidth
    ) {
        TextButton[] moveButtons = new TextButton[4];
        for (int i = 0; i < 4; i++) {
            TextButton.TextButtonStyle moveButtonStyle = new TextButton.TextButtonStyle();
            moveButtonStyle.font = font;
            moveButtonStyle.up = skin.getDrawable("default-select");
            final TextButton moveButton = new TextButton(moveNames.get(i), moveButtonStyle);
            if (!moveNames.get(i).equals("-")) {
                int finalI = i;
                moveButtonStyle.down = skin.getDrawable("default-select-selection");
                moveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Tuple2<Object, String> effective = Info.getEffectiveDamage(moveNames.get(finalI), enemy);
                        float dmg = (float) effective._1 * (minWidth / 100);
                        float remainingHealth = enemyHealthBar.getWidth() - dmg;
                        if (remainingHealth <= 0) {
                            enemyHealthBar.setWidth(0);
                            battleDescriptionLabel.setText("You have defeated the enemy " + enemy.name() + "!" + " You have gained 50 exp");
                            ally.gainExp(50);
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    mainGame.setScreen(pokemonScreen);
                                }
                            }, 2);
                        }
                        else {
                            enemyHealthBar.setWidth(remainingHealth);
                            setColorStatus(enemyHealthBar);
                            battleDescriptionLabel.setText(effective._2);
                        }
                    }
                });
            }
            else moveButton.setDisabled(true);
            moveButtons[i] = moveButton;
        }
        return moveButtons;
    }

    private Table createMoveButtonsTable(TextButton[] moveButtons) {
        Table table = new Table();
        table.add(moveButtons[0]).expand().fill();
        table.add(moveButtons[1]).expand().fill();
        table.row();
        table.add(moveButtons[2]).expand().fill();
        table.add(moveButtons[3]).expand().fill();
        table.setSize(MainGame.WORLD_WIDTH, MainGame.WORLD_HEIGHT / 3);
        return table;
    }

    private void setColorStatus(ProgressBar healthBar) {
        if (healthBar.getWidth() <= 90) healthBar.setColor(Color.RED);
        else healthBar.setColor(Color.GREEN);
    }

}