package gdx.myGames.Breakout_Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * The Block object that the player will control in order to hit the ball.
 */
public abstract class Player extends Block
{
    private static final float SPEED = 500f;           // How fast the player is moving

    /**
     * Constructor for the Player class
     */
    public Player(int width, int height)
    {
        super(width, height);
    }

    /**
     * Puts player at start location
     */
    protected abstract void reset();

    /**
     * Checks whether the player is within the games borders,
     * and stops the player from moving outside of the games borders.
     */
    public abstract void checkWorldBorders();

    /**
     * Moves the Block the player is controlling either left or right,
     * depending on the parameter.
     * @param key - The direction the player is moving.
     */
    public void move(int key)
    {
        float distance = SPEED * Gdx.graphics.getDeltaTime();

        switch (key) {
            case Input.Keys.A:
                block.x -= distance;
                break;
            case Input.Keys.D:
                block.x += distance;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                block.y += distance;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                block.y -= distance;
                break;
        }
    }
}
