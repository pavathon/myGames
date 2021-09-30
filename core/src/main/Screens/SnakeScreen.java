package Screens;

import Snake.Food;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import Snake.Snake;

/**
 * Screen for the Snake game.
 */
public class SnakeScreen extends ScreenAdapter
{
    private final MainGame game;

    private Snake snake;
    private Food food;
    private int scoreInt;

    public SnakeScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        snake = new Snake();
        food = new Food();
        scoreInt = 0;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.ESCAPE:
                        game.setScreen(game.menuScreen);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta)
    {
        /*
        try {
            Thread.sleep((long)(1000/20-Gdx.graphics.getDeltaTime()));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

         */

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render all the objects displayed in the screen
        game.render.begin(ShapeRenderer.ShapeType.Filled);
        snake.render(game.render, Color.GREEN);
        food.render(game.render, Color.RED);
        game.render.end();

        // Display the score
        game.batch.begin();
        // Score text and lives text
        game.font.draw(game.batch, "Score: " + scoreInt, 50, MainGame.WORLD_HEIGHT - 20);
        game.batch.end();

        // Move the snake
        snake.advance();

        // Collision checks
        if (snake.checkFood(food)) {
            food.placeFood();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            snake.grow();
            scoreInt++;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) snake.move(Input.Keys.W);
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) snake.move(Input.Keys.S);
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) snake.move(Input.Keys.A);
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) snake.move(Input.Keys.D);
        else snake.move(0);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }
}
