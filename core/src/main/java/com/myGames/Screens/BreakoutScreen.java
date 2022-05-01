package com.myGames.Screens;

import com.myGames.Breakout_Pong.Ball;
import com.myGames.Breakout_Pong.Breakout.BlockGroup;
import com.myGames.Breakout_Pong.Breakout.BreakoutPlayer;
import com.myGames.Breakout_Pong.Breakout.RectangleSides;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Screen class which displays the Breakout game
 */
public class BreakoutScreen extends ScreenAdapter
{
    private final MainGame game;

    private BreakoutPlayer player;		// Player object controlled by player
    private Ball ball;					// Ball used in game
    private BlockGroup allBlocks;		// The blocks that the player needs to hit
    private int scoreInt, livesInt;     // Keeps track of the player's score and lives
    private Sound ballSound;            // Sound the ball makes when colliding with objects

    /**
     * Constructor for the BreakoutScreen class
     * @param game - Main game class
     */
    public BreakoutScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) game.setScreen(game.menuScreen);
                return true;
            }
        });

        player = new BreakoutPlayer();
        ball = new Ball();
        allBlocks = new BlockGroup(6, 4);
        allBlocks.groupBlocks();
        scoreInt = 0;
        livesInt = 3;

        ballSound = Gdx.audio.newSound(Gdx.files.internal("sounds/plop.ogg"));
    }

    @Override
    public void render(float v)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render all the objects displayed in the screen
        game.render.begin(ShapeRenderer.ShapeType.Filled);
        player.render(game.render, Color.WHITE);
        ball.render(game.render);
        allBlocks.renderHitBlocks(game.render);
        game.render.end();

        // Renders the score text in the game
        game.batch.begin();
        // Score text and lives text
        game.font.draw(game.batch, "Score: " + scoreInt, MainGame.WORLD_WIDTH / 2 - 50, MainGame.WORLD_HEIGHT - 20);
        game.font.draw(game.batch, "Lives: " + livesInt, 100, MainGame.WORLD_HEIGHT - 20);
        game.batch.end();

        // Collision checks
        player.checkWorldBorders();
        boolean checkLose = ball.checkWorldBordersBreakout(ballSound);
        if (checkLose) {
            ball.reset();
            livesInt--;
            if (livesInt == 0) {
                game.gameOverScreen.setgameovertxt("YOU LOST...");
                game.setScreen(game.gameOverScreen);
            }
        }
        else if (scoreInt == allBlocks.getArea()) {
            game.gameOverScreen.setgameovertxt("YOU WON!");
            game.setScreen(game.gameOverScreen);
        }

        // Checks whether the ball has collided with any blocks in the game
        if (ball.overlaps(player.getBlock())) ballSound.play();
        if (allBlocks.checkAllOverlaps(ball)) {
            scoreInt++;
            ballSound.play();
        }

        // Moves the player block
        if (Gdx.input.isKeyPressed(Input.Keys.A)) player.move(Input.Keys.A);
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) player.move(Input.Keys.D);

        ball.move();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose()
    {
        ballSound.dispose();
    }

    /**
     * Checks whether two circles are colliding
     * @param c1 - First circle.
     * @param c2 - Second circle.
     * @return True if circles do are colliding, otherwise False.
     */
    private boolean overlaps(Circle c1, Circle c2)
    {
        float a = Math.abs(c1.x - c2.x);
        float b = Math.abs(c1.y - c2.y);
        float distance = (float) Math.sqrt((a * a) + (b * b));
        return distance < c1.radius + c2.radius;
    }

    /**
     * Checks whether two rectangles are colliding
     * @param r1 - First rectangle.
     * @param r2 - Second rectangle.
     * @return True if rectangles are colliding
     */
    private boolean overlaps(Rectangle r1, Rectangle r2)
    {
        float topEdge1 = r1.y + r1.height;
        float bottomEdge1 = r1.y;
        float rightEdge1 = r1.x + r1.width;
        float leftEdge1 = r1.x;

        float topEdge2 = r2.y + r2.height;
        float bottomEdge2 = r2.y;
        float rightEdge2 = r2.x + r2.width;
        float leftEdge2 = r2.x;

        return leftEdge1 < rightEdge2 && rightEdge1 > leftEdge2 && bottomEdge1 < topEdge2 && topEdge1 > bottomEdge2;
    }

    /**
     * Checks whether a circle and rectangle are overlapping
     * @param c - Circle.
     * @param r - Rectangle.
     * @return True if the circle are overlapping, otherwise False.
     */
    public static RectangleSides overlaps(Circle c, Rectangle r)
    {
        float r_centre_x = r.width / 2;
        float r_centre_y = r.height / 2;

        // Distances between rectangle and circle centre in each axis
        float distance_x = Math.abs(c.x - (r.x + r_centre_x));
        float distance_y = Math.abs(c.y - (r.y + r_centre_y));

        // Checks whether the ball is close enough to the rectangle
        if (distance_x > (r_centre_x + c.radius) || distance_y > (r_centre_y + c.radius)) {
            return RectangleSides.NONE;
        }

        // Check whether the ball is directly on top, below, left or right of the rectangle when colliding
        if (distance_x <= (r_centre_x)) {
            return RectangleSides.HORIZONTAL;
        }
        else if (distance_y <= r_centre_y) {
            return RectangleSides.VERTICAL;
        }

        // Check whether the ball is colliding with the rectangle through the corners
        double distance_corner = Math.sqrt(Math.pow(distance_x - r_centre_x, 2) + Math.pow(distance_y - r_centre_y, 2));

        if (distance_corner <= c.radius) {
            return RectangleSides.CORNER;
        }

        return RectangleSides.NONE;
    }
}
