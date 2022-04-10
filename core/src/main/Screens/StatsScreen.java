package Screens;

import PokemonInfo.Player;
import PokemonInfo.Pokemon;
import Screens.Pokemon.PokemonScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class StatsScreen extends ScreenAdapter
{
    private final MainGame game;
    private final PokemonScreen pokemonScreen;
    private final Player player;

    public StatsScreen(MainGame game, PokemonScreen pokemonScreen)
    {
        this.game = game;
        this.pokemonScreen = pokemonScreen;
        this.player = Player.getPlayerInstance();
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) game.setScreen(pokemonScreen);
                return true;
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        for (Pokemon pokemon : player.getPokemon()) {
            if (pokemon == null) continue;
            game.font.draw(game.batch, "Name: " + pokemon.name(), Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .85f);
            game.font.draw(game.batch, "Type: " + pokemon.pokemonType().toString(), Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .65f);
            game.font.draw(game.batch, "Moves: " + pokemon.moveSetToString(), Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .6f);
        }
        game.batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }
}