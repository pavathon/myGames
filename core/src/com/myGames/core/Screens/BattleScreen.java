package com.myGames.core.Screens;

import com.badlogic.gdx.Gdx;
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
import com.myGames.core.MainGame;
import com.myGames.core.Pokemon.*;

import java.util.stream.Stream;

class BattleScreen extends ScreenAdapter {
    private final Stage stage = new Stage();

    private final Skin skin = createSkin();

    private final MainGame game;
    private final PokemonScreen pokemonScreen;
    private final Player player;

    public BattleScreen(MainGame game, PokemonScreen pokemonScreen) {
        this.game = game;
        this.pokemonScreen = pokemonScreen;
        player = Player.getInstance();
    }

    private Timer.Task endBattle() {
        return new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(pokemonScreen);
                GameSave.saveGame();
            }
        };
    }

    private Timer.Task allyTurn(Label battleDescriptionLabel, String pokemonName, TextButton[] moveButtons) {
        return new Timer.Task() {
            @Override
            public void run() {
                String text = String.format("What will %s do?", pokemonName);
                battleDescriptionLabel.setText(text);
                toggleMoveButtons(moveButtons, false);
            }
        };
    }

    private Timer.Task enemyTurn(Label battleDescriptionLabel) {
        return new Timer.Task() {
            @Override
            public void run() {
                battleDescriptionLabel.setText("It's the enemy turn!");
            }
        };
    }

    private Timer.Task enemyAttack(
            Label battleDescriptionLabel,
            AllyPokemon ally,
            EnemyPokemon enemy,
            ProgressBar allyHealthBar,
            TextButton[] moveButtons
    ) {
        return new Timer.Task() {
            @Override
            public void run() {
                Move attackMove = enemy.attack();
                Effective eff = Info.getEffectiveDamage(attackMove.getName(), enemy);
                float remainingHealth = attackOpponent(attackMove.getName(), eff.getMultiplier(), allyHealthBar);
                if (remainingHealth <= 0) {
                    allyHealthBar.setWidth(0);
                    battleDescriptionLabel.setText("You died!");
                    Timer.schedule(endBattle(), 2);
                } else {
                    allyHealthBar.setWidth(remainingHealth);
                    setColorStatus(allyHealthBar);
                    battleDescriptionLabel.setText(eff.toString());
                    Timer.schedule(allyTurn(battleDescriptionLabel, ally.getName(), moveButtons), 2);
                }
            }
        };
    }

    public void show() {
        createBattle();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void createBattle() {
        Gdx.input.setInputProcessor(stage);
        AllyPokemon[] playerPokemon = player.getPokemonBag();
        EnemyPokemon enemyPokemon = Info.getRandomEnemyPokemon();

        BitmapFont font = createFont(3f);

        int minWidth = 500;
        int minHeight = 100;

        ProgressBar.ProgressBarStyle healthBarStyle = createHealthBarStyle(skin, minWidth, minHeight);
        ProgressBar allyHealthBar = createHealthBar(healthBarStyle, minWidth, minHeight);
        allyHealthBar.setPosition(50, MainGame.WORLD_HEIGHT - 150);
        ProgressBar enemyHealthBar = createHealthBar(healthBarStyle, minWidth, minHeight);
        enemyHealthBar.setPosition(MainGame.WORLD_WIDTH - enemyHealthBar.getWidth() - 50, MainGame.WORLD_HEIGHT - 150);

        Label.LabelStyle nameLabelStyle = createNameLabelStyle(font);
        Label allyNameLabel = createNameLabel(playerPokemon[0].getName(), nameLabelStyle, allyHealthBar);
        Label enemyNameLabel = createNameLabel(enemyPokemon.getName(), nameLabelStyle, enemyHealthBar);

        Image allyPokemonImage = createAllyPokemonImage();

        Label battleDescriptionLabel = createBattleDescriptionLabel(playerPokemon[0].getName());

        String[] moveNames = playerPokemon[0].getMoveNames();
        TextButton[] moveButtons = createMoveButtons(font, moveNames);
        addMoveButtonListeners(moveButtons, allyHealthBar, playerPokemon[0], enemyHealthBar, enemyPokemon, battleDescriptionLabel);
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

    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.clear();
    }

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
        BitmapFont font = new BitmapFont(
                Gdx.files.internal("core/assets/gdx-skins/sgx/skin/font-export.fnt"),
                Gdx.files.internal("core/assets/gdx-skins/sgx/raw/font-export.png"),
                false
        );
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

    private ProgressBar createHealthBar(ProgressBar.ProgressBarStyle healthBarStyle, int minWidth, int minHeight) {
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

    private Label createBattleDescriptionLabel(String pokemonName) {
        BitmapFont font = createFont(2f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new Label(String.format("What will %s do?", pokemonName), labelStyle);
    }

    private Image createAllyPokemonImage() {
        Image allyImg = new Image(new Texture(Gdx.files.internal("gigachad.png")));
        allyImg.setPosition(50, 600);
        allyImg.setScale(0.5f);
        return allyImg;
    }

    private TextButton[] createMoveButtons(BitmapFont font, String[] moveNames) {
        return Stream
                .of(moveNames)
                .map(moveName -> {
                    TextButton.TextButtonStyle moveButtonStyle = new TextButton.TextButtonStyle();
                    moveButtonStyle.font = font;
                    moveButtonStyle.up = skin.getDrawable("default-select");
                    moveButtonStyle.down = skin.getDrawable("default-select-selection");
                    return new TextButton(moveName, moveButtonStyle);
                })
                .toArray(TextButton[]::new);
    }


    private void addMoveButtonListeners(
            TextButton[] moveButtons,
            ProgressBar allyHealthbar,
            AllyPokemon ally,
            ProgressBar enemyHealthBar,
            EnemyPokemon enemy,
            Label battleDescriptionLabel
    ) {
        for (TextButton moveButton : moveButtons) {
            if (moveButton.getText().toString().equals("-")) {
                moveButton.getStyle().down = null;
                moveButton.setDisabled(true);
            } else {
                moveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Effective eff = Info.getEffectiveDamage(moveButton.getText().toString(), enemy);
                        float remainingHealth = attackOpponent(moveButton.getText().toString(), eff.getMultiplier(), enemyHealthBar);
                        if (remainingHealth <= 0) {
                            enemyHealthBar.setWidth(0);
                            toggleMoveButtons(moveButtons, true);
                            String battleText = String.format("You have defeated the enemy %s! You have gained 50 exp", enemy.getName());
                            battleDescriptionLabel.setText(battleText);
                            ally.gainExp(50);
                            Timer.schedule(endBattle(), 2);
                        } else {
                            enemyHealthBar.setWidth(remainingHealth);
                            setColorStatus(enemyHealthBar);
                            battleDescriptionLabel.setText(eff.toString());
                            Timer.schedule(enemyTurn(battleDescriptionLabel), 1.5f);
                            toggleMoveButtons(moveButtons, true);
                            Timer.schedule(enemyAttack(battleDescriptionLabel, ally, enemy, allyHealthbar, moveButtons), 3);
                        }
                    }
                });
            }
        }
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

    private float attackOpponent(String attackerMoveName, float effective, ProgressBar opponentHealthBar) {
        int moveDmg = Info.ALL_MOVES.get(attackerMoveName).getDamage();
        float effectiveDmg = moveDmg * effective;
        float dmg = effectiveDmg * (opponentHealthBar.getStyle().background.getMinWidth() / 100);
        return opponentHealthBar.getWidth() - dmg;
    }

    private void toggleMoveButtons(TextButton[] moveButtons, boolean isDisabled) {
        for (TextButton button : moveButtons) {
            if (!button.getText().toString().equals("-")) {
                if (isDisabled) button.getStyle().down = null;
                else button.getStyle().down = skin.getDrawable("default-select-selection");
                button.setDisabled(isDisabled);
            }
        }
    }

    private void setColorStatus(ProgressBar healthBar) {
        if (healthBar.getWidth() <= 90) healthBar.setColor(Color.RED);
        else healthBar.setColor(Color.GREEN);
    }
}