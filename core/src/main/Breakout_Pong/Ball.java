package Breakout_Pong;

import Breakout_Pong.Breakout.RectangleSides;
import Breakout_Pong.Pong.PlayerNum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import Screens.BreakoutScreen;
import Screens.MainGame;

import java.util.Random;

public class Ball
{
    private static final int RADIUS = 10;     // Radius of the ball

    private final Circle ball;                // Actual body of the ball
    private Vector2 velocity;                 // Velocity of the ball

    /**
     * Constructor for the ball class.
     */
    public Ball()
    {
        ball = new Circle();
        ball.radius = RADIUS;
        reset();
    }

    /**
     * Puts ball at its start location
     */
    public void reset()
    {
        ball.setPosition(MainGame.WORLD_WIDTH / 2, MainGame.WORLD_HEIGHT / 2);

        float velX = 500;
        float velY = -200;
        velocity = new Vector2(randVelX(velX), velY);
    }

    /**
     * Getter method for the ball field.
     * @return The actual body of the ball
     */
    public Circle getCircle()
    {
        return ball;
    }

    /**
     * Renders the ball into the screen.
     * @param r ShapeRenderer object used to render the ball.
     */
    public void render(ShapeRenderer r)
    {
        r.setColor(Color.WHITE);
        r.circle(ball.x, ball.y, ball.radius);
    }

    /**
     * @return The vertical velocity of the ball, either positive or negative.
     */
    private float randVelX(float velX)
    {
        return new Random().nextBoolean() ? velX : -velX;
    }

    /**
     * Moves the ball.
     */
    public void move()
    {
        ball.x += velocity.x * Gdx.graphics.getDeltaTime();
        ball.y += velocity.y * Gdx.graphics.getDeltaTime();
    }

    /**
     * Simulates a ball bouncing off a screen.
     * Also checks whether the player lost the life.
     * For the Breakout game.
     * @return True if ball has reached bottom of screen, otherwise False.
     */
    public boolean checkWorldBordersBreakout(Sound sound)
    {
        if (ball.y + ball.radius >= MainGame.WORLD_HEIGHT) {
            ball.y = MainGame.WORLD_HEIGHT - ball.radius;
            velocity.y *= -1;
            sound.play();
        }
        else if (ball.y - ball.radius <= 0) return true;

        if (ball.x + ball.radius >= MainGame.WORLD_WIDTH) {
            ball.x = MainGame.WORLD_WIDTH - ball.radius;
            velocity.x *= -1;
            sound.play();
        }
        else if (ball.x - ball.radius <= 0) {
            ball.x = ball.radius;
            velocity.x *= -1;
            sound.play();
        }
        return false;
    }

    /**
     * Simulates a ball bouncing off a screen.
     * Also checks whether a player scored.
     * For the Pong game.
     * @return True if ball has reached bottom of screen, otherwise False.
     */
    public PlayerNum checkWorldBordersPong()
    {
        if (ball.y + ball.radius >= MainGame.WORLD_HEIGHT) {
            ball.y = MainGame.WORLD_HEIGHT - ball.radius;
            velocity.y *= -1;
        }
        else if (ball.y - ball.radius <= 0) {
            ball.y = ball.radius;
            velocity.y *= -1;
        }
        if (ball.x + ball.radius >= MainGame.WORLD_WIDTH) {
            return PlayerNum.PLAYER1;
        }
        else if (ball.x - ball.radius <= 0) {
            return PlayerNum.PLAYER2;
        }
        return null;
    }

    /**
     * Checks whether the ball has touched a block
     * @param block Block to be tested.
     */
    public boolean overlaps(Rectangle block)
    {
        RectangleSides overlap = BreakoutScreen.overlaps(ball, block);
        if (overlap == RectangleSides.HORIZONTAL) {
            velocity.y *= -1;
            return true;
        }
        else if (overlap == RectangleSides.VERTICAL) {
            velocity.x *= -1;
            return true;
        }
        else if (overlap == RectangleSides.CORNER) {
            velocity.x *= -1;
            velocity.y *= -1;
            return true;
        }
        else return false;
    }
}
