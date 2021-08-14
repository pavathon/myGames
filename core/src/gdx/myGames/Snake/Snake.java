package gdx.myGames.Snake;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import gdx.myGames.MainGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The snake that the player will control
 */
public class Snake
{
    private static final int STARTING_SIZE = 4, LENGTH = 15;   // How long the snake is and the length of each body
    private static final float SPEED = 150;                      // How fast the snake moves

    private static final Map<Integer, Vector2> directions;      // The velocity of the snake depending on key pressed.
    static {
        directions = new HashMap<>();
        directions.put(Input.Keys.W, new Vector2(0, SPEED));
        directions.put(Input.Keys.S, new Vector2(0, -SPEED));
        directions.put(Input.Keys.A, new Vector2(-SPEED, 0));
        directions.put(Input.Keys.D, new Vector2(SPEED, 0));
    }

    private final ArrayList<SnakeBody> body;                    // Snake body made up of snake body objects.
    private final ArrayList<Integer> counters;                  // Keeps track of the different velocities of the snake
    private final SnakeHead head;                               // Head of the snake.

    public Snake()
    {
        body = new ArrayList<>(STARTING_SIZE);
        createBody();
        counters = new ArrayList<>();
        counters.add(1);
        head = (SnakeHead) body.get(0);
    }

    /**
     * Instantiates the blocks making up the snake body
     */
    private void createBody()
    {
        int count = STARTING_SIZE / 2;

        body.add(new SnakeHead(MainGame.WORLD_WIDTH / 2,
                (MainGame.WORLD_HEIGHT / 2) + (count * LENGTH),
                LENGTH, LENGTH, 0, SPEED));
        count--;

        for (int i = 1; i < STARTING_SIZE; i++) {
            body.add(new SnakeBody(MainGame.WORLD_WIDTH / 2,
                    (MainGame.WORLD_HEIGHT / 2) + (count * LENGTH),
                    LENGTH, LENGTH));
            count--;
        }
    }

    /**
     * Sets the velocity of the head when a key is pressed and moves each
     * part of the body after every time skip.
     * @param key What key was pressed or 0 if no key was pressed.
     */
    public void move(int key)
    {
        if (!counters.isEmpty()) {
            for (int i = 0; i < counters.size(); i++) {
                int counter = counters.get(i);
                counters.set(i, counter + 1);
            }
            if (counters.get(0) == body.size()) counters.remove(0);
        }

        Vector2 vel = head.getVelocity();
        switch (key) {
            case Input.Keys.W:
            case Input.Keys.S:
                if (vel.x != 0) {
                    head.setVelocity(directions.get(key));
                    counters.add(1);
                }
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                if (vel.y != 0) {
                    head.setVelocity(directions.get(key));
                    counters.add(1);
                }
                break;
        }
    }

    /**
     * Moves the snake by 1 unit (1 unit could be anything)
     */
    public void advance()
    {
        float nextx, nexty, currx, curry;

        nextx = head.getRect().x;
        nexty = head.getRect().y;
        head.move();
        for (int i = 1; i < body.size(); i++) {
            currx = body.get(i).getRect().x;
            curry = body.get(i).getRect().y;
            body.get(i).getRect().setPosition(nextx, nexty);
            nextx = currx;
            nexty = curry;
        }
    }

    /**
     * Grows the snake by adding a new snake body at the tail.
     */
    public void grow()
    {
        body.add(new SnakeBody(-100, 0, LENGTH, LENGTH));
    }

    /**
     * Check whether the snake has eaten the food.
     * @param food The food to be eaten.
     * @return True if snake has eaten the food, otherwise false.
     */
    public boolean checkFood(Food food)
    {
        return body.get(0).getRect().overlaps(food.getRect());
    }

    /**
     * Renders the snake on the screen.
     * @param r ShapeRenderer used to render the object.
     * @param col Color of the object when rendered.
     */
    public void render(ShapeRenderer r, Color col)
    {
        for (SnakeBody sb : body) sb.render(r, col);
    }
}
