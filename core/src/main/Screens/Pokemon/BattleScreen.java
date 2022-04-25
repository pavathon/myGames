package Screens.Pokemon;

import PokemonInfo.Info;
import PokemonInfo.Player;
import Screens.MainGame;
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

import java.util.List;

public class BattleScreen extends ScreenAdapter
{
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
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas("gdx-skins/default/skin/uiskin.atlas");
        skin.addRegions(buttonAtlas);

        int minWidth = 300;
        int minHeight = 100;

        // Style of the health bar
        ProgressBar.ProgressBarStyle healthBarStyle = new ProgressBar.ProgressBarStyle();
        healthBarStyle.background = skin.getDrawable("default-slider");
        healthBarStyle.disabledBackground = skin.getDrawable("default-slider");
        healthBarStyle.background.setMinWidth(minWidth);
        healthBarStyle.background.setMinHeight(minHeight);
        healthBarStyle.disabledBackground.setMinWidth(minWidth);
        healthBarStyle.disabledBackground.setMinHeight(minHeight);

        // Enemy Health Bar
        ProgressBar enemyHealthBar = new ProgressBar(0, 100, 1f, false, healthBarStyle);
        enemyHealthBar.setSize(minWidth, minHeight);
        enemyHealthBar.setPosition(MainGame.WORLD_WIDTH - enemyHealthBar.getWidth() - 50, MainGame.WORLD_HEIGHT - 150);
        enemyHealthBar.setColor(Color.GREEN);

        // Ally Health Bar
        ProgressBar allyHealthBar = new ProgressBar(0, 100, 1f, false, healthBarStyle);
        allyHealthBar.setSize(minWidth, minHeight);
        allyHealthBar.setPosition(50, MainGame.WORLD_HEIGHT - 150);
        allyHealthBar.setColor(Color.GREEN);

        Image allyImg = new Image(new Texture(Gdx.files.internal("gigachad.png")));
        allyImg.setPosition(50, 600);
        allyImg.setScale(0.5f);

        List<String> moveNames = Player.getPlayerInstance().getPokemon().get(0).getMoveNames();

        BitmapFont font = new BitmapFont(Gdx.files.internal("core/assets/gdx-skins/default/skin/default.fnt"),
                Gdx.files.internal("core/assets/gdx-skins/default/raw/default.png"), false);
        font.getData().setScale(3f);

        TextButton[] moveButtons = new TextButton[4];
        for (int i = 0; i < 4; i++) {
            TextButton.TextButtonStyle moveButtonStyle = new TextButton.TextButtonStyle();
            moveButtonStyle.font = font;
            moveButtonStyle.up = skin.getDrawable("default-select");
            final TextButton moveButton = new TextButton(moveNames.get(i), moveButtonStyle);
            if (!moveNames.get(i).equals("-")) moveButtonStyle.down = skin.getDrawable("default-select-selection");
            else moveButton.setDisabled(true);
            int finalI = i;
            moveButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    enemyHealthBar.setWidth(enemyHealthBar.getWidth() - Info.getDamage(moveNames.get(finalI)));
                    setColorStatus(enemyHealthBar);
                }
            });
            moveButtons[i] = moveButton;
        }

        Table table = new Table();
        table.add(moveButtons[0]).expand().fill();
        table.add(moveButtons[1]).expand().fill();
        table.row();
        table.add(moveButtons[2]).expand().fill();
        table.add(moveButtons[3]).expand().fill();
        table.setSize(MainGame.WORLD_WIDTH, MainGame.WORLD_HEIGHT / 3);

        stage.addActor(table);
        stage.addActor(enemyHealthBar);
        stage.addActor(allyHealthBar);
        stage.addActor(allyImg);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void setColorStatus(ProgressBar healthBar) {
        if (healthBar.getWidth() <= 90) healthBar.setColor(Color.RED);
        else healthBar.setColor(Color.GREEN);
    }
}