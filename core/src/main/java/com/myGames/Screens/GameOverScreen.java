package com.myGames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Screen class which holds the game over screen
 */
public class GameOverScreen extends ScreenAdapter
{
    private final MainGame game;
    private String gameoverTxt;

    /**
     * Constructor for the GameOverScreen class
     * @param game - Main game class
     */
    public GameOverScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(game.breakoutScreen);
                }
                else if (keyCode == Input.Keys.ESCAPE) {
                    game.setScreen(game.menuScreen);
                }
                return true;
            }
        });
    }

    @Override
    public void render(float v)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, gameoverTxt, Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .35f);
        game.font.draw(game.batch, "Press SPACE to play again", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .35f);
        game.font.draw(game.batch, "Press ESC to quit to main menu", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    public void setgameovertxt(String txt)
    {
        gameoverTxt = txt;
    }
}

