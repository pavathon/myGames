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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.List;

public class BattleScreen extends ScreenAdapter
{
    private final MainGame mainGame;
    private final PokemonScreen pokemonScreen;

    private Stage stage;

    public BattleScreen(MainGame game, PokemonScreen pokemonScreen)
    {
        this.mainGame = game;
        this.pokemonScreen = pokemonScreen;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) mainGame.setScreen(pokemonScreen);
                return true;
            }
        });

        createButtons();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    private void createButtons() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas("gdx-skins/default/skin/uiskin.atlas");
        skin.addRegions(buttonAtlas);

        // Style of the health bar
        ProgressBar.ProgressBarStyle healthBarStyle = new ProgressBar.ProgressBarStyle();
        healthBarStyle.background = skin.getDrawable("default-slider");
        healthBarStyle.disabledBackground = skin.getDrawable("default-slider");
        healthBarStyle.background.setMinWidth(300);
        healthBarStyle.background.setMinHeight(100);
        healthBarStyle.disabledBackground.setMinWidth(300);
        healthBarStyle.disabledBackground.setMinHeight(100);

        // Health Bar
        ProgressBar healthBar = new ProgressBar(0, 100, 1f, false, healthBarStyle);
        healthBar.setSize(300, 100);
        healthBar.setPosition(1000, 1150);

        List<String> moveNames = Player.getPlayerInstance().getPokemon().get(0).getMoveNames();

        BitmapFont font = new BitmapFont(Gdx.files.internal("core/assets/gdx-skins/default/skin/default.fnt"),
                Gdx.files.internal("core/assets/gdx-skins/default/raw/default.png"), false);
        font.getData().setScale(3f);

        TextButton.TextButtonStyle move1ButtonStyle = new TextButton.TextButtonStyle();
        move1ButtonStyle.font = font;
        move1ButtonStyle.up = skin.getDrawable("default-select");
        move1ButtonStyle.down = skin.getDrawable("default-select-selection");
        final TextButton move1Button = new TextButton(moveNames.get(0), move1ButtonStyle);
        move1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                healthBar.setWidth(healthBar.getWidth() - 20);
                setColorStatus(healthBar);
            }
        });

        TextButton.TextButtonStyle move2ButtonStyle = new TextButton.TextButtonStyle();
        move2ButtonStyle.font = font;
        move2ButtonStyle.up = skin.getDrawable("default-select");
        move2ButtonStyle.down = skin.getDrawable("default-select-selection");
        final TextButton move2Button = new TextButton(moveNames.get(1), move2ButtonStyle);

        TextButton.TextButtonStyle move3ButtonStyle = new TextButton.TextButtonStyle();
        move3ButtonStyle.font = font;
        move3ButtonStyle.up = skin.getDrawable("default-select");
        move3ButtonStyle.down = skin.getDrawable("default-select-selection");
        final TextButton move3Button = new TextButton(moveNames.get(2), move3ButtonStyle);

        TextButton.TextButtonStyle move4ButtonStyle = new TextButton.TextButtonStyle();
        move4ButtonStyle.font = font;
        move4ButtonStyle.up = skin.getDrawable("default-select");
        move4ButtonStyle.down = skin.getDrawable("default-select-selection");
        final TextButton move4Button = new TextButton(moveNames.get(3), move4ButtonStyle);
        move4Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        Table table = new Table();
        table.add(move1Button).expand().fill();
        table.add(move2Button).expand().fill();
        table.row();
        table.add(move3Button).expand().fill();
        table.add(move4Button).expand().fill();
        table.setSize(MainGame.WORLD_WIDTH, MainGame.WORLD_HEIGHT / 2);

        stage.addActor(table);
        stage.addActor(healthBar);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

    private void setColorStatus(ProgressBar healthBar) {
        if (healthBar.getWidth() <= 90) healthBar.setColor(Color.RED);
        else healthBar.setColor(Color.GREEN);
    }
}