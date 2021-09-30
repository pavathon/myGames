package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Screen class which holds the Main Menu
 */
public class MenuScreen extends ScreenAdapter
{
    private final MainGame game;

    /**
     * Constructor for the MenuScreen class
     * @param game - Main game class
     */
    public MenuScreen(MainGame game)
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
                    case Input.Keys.NUM_1:
                        game.setScreen(game.breakoutScreen);
                        break;
                    case Input.Keys.NUM_2:
                        game.setScreen(game.pongScreen);
                        break;
                    case Input.Keys.NUM_3:
                        game.setScreen(game.snakeScreen);
                        break;
                    case Input.Keys.NUM_4:
                        game.setScreen(game.spaceScreen);
                        break;
                    case Input.Keys.NUM_5:
                        game.setScreen(game.pokemonScreen);
                        break;
                    case Input.Keys.ESCAPE:
                        System.exit(0);
                        break;
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
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .4f, Gdx.graphics.getHeight() * .85f);
        game.font.draw(game.batch, "1. Breakout", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(game.batch, "2. Pong", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .6f);
        game.font.draw(game.batch, "3. Snake", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .55f);
        game.font.draw(game.batch, "4. Space", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "5. Pokemon", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .45f);
        game.font.draw(game.batch, "ESC to quit", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .15f);
        game.batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }
}
