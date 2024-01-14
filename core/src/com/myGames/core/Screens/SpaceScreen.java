package com.myGames.core.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.myGames.core.MainGame;
import com.myGames.core.Space.Player;


public class SpaceScreen extends ScreenAdapter
{
    private final MainGame game;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private TextureAtlas textureAtlas;
    private Player player;

    /**
     * Constructor for the MenuScreen class
     * @param game - Image game class
     */
    public SpaceScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) {
                    System.exit(0);
                }
                return true;
            }
        });

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(MainGame.WORLD_WIDTH, MainGame.WORLD_HEIGHT, camera);
        textureAtlas = new TextureAtlas("skins/shapes.txt");
        player = Player.getInstance(textureAtlas);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        player.render(game.batch);
        game.batch.end();

        player.move();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) player.shoot();

        player.moveLasers(game.render);
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
        game.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void hide()
    {
        textureAtlas.dispose();
    }
}
