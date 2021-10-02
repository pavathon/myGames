package Screens.Pokemon;

import Screens.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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

        createButton();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    private void createButton()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas("gdx-skins/default/skin/uiskin.atlas");
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = mainGame.font;
        textButtonStyle.up = skin.getDrawable("default-select");
        textButtonStyle.down = skin.getDrawable("default-select");
        textButtonStyle.checked = skin.getDrawable("default-select-selection");
        final TextButton button = new TextButton("Button1", textButtonStyle);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                button.setText("it changed");
            }
        });

        Table table = new Table();
        table.add(button);
        table.setFillParent(true);

        stage.addActor(table);
    }
}