package com.myGames.core.Screens.Pokemon;

import com.myGames.core.Screens.PokemonScreen;
import com.myGames.core.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class IntroductionScreen extends ScreenAdapter
{
    private final MainGame game;

    public IntroductionScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.ESCAPE:
                        game.setScreen(game.menuScreen);
                        break;
                    case Input.Keys.NUM_1:
                        game.setScreen(new StarterPokemonScreen(game));
                        break;
                    case Input.Keys.NUM_2:
                        game.setScreen(new PokemonScreen(game));
                }
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
        game.font.draw(game.batch, "Welcome to Pokemon!", Gdx.graphics.getWidth() * .4f, Gdx.graphics.getHeight() * .85f);
        game.font.draw(game.batch, "1. New game", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .55f);
        game.font.draw(game.batch, "2. Load save game", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }
}