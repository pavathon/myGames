package com.myGames.Screens.Pokemon;

import com.myGames.PokemonInfoScala.Info;
import com.myGames.PokemonInfo.Player;
import com.myGames.Screens.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class PokemonScreen extends ScreenAdapter
{
    private final MainGame game;
    private final BattleScreen battleScreen;
    private final StatsScreen statsScreen;

    public PokemonScreen(MainGame game, String starterPokemonName)
    {
        this.game = game;
        battleScreen = new BattleScreen(game, this);
        statsScreen = new StatsScreen(game, this);
        Player player = Player.getPlayerInstance();
        player.addStarterPokemon(Info.getStarterPokemon(starterPokemonName));
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) game.setScreen(game.menuScreen);
                else if (keyCode == Input.Keys.NUM_1) game.setScreen(battleScreen);
                else if (keyCode == Input.Keys.NUM_2) game.setScreen(statsScreen);
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
        game.font.draw(game.batch, "Welcome to PokemonInfo.Pokemon!", Gdx.graphics.getWidth() * .4f, Gdx.graphics.getHeight() * .85f);
        game.font.draw(game.batch, "Press 1 to battle a pokemon.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(game.batch, "Press 2 to look at your pokemon", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .6f);
        game.batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }
}