package com.myGames.core.Snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.myGames.core.MainGame;

import java.util.Random;

/**
 * Food that the snake will eat in order to grow and score points.
 */
public class Food
{
    private final Rectangle rect;
    private final Random rand;

    /**
     * Constructor for the Food class.
     */
    public Food()
    {
        rand = new Random();
        rect = new Rectangle();
        rect.setSize(20, 20);
        placeFood();
    }

    /**
     * Places the food in a random spot in the screen.
     */
    public void placeFood()
    {
        int border = 50;
        rect.setCenter(border + rand.nextFloat() * (MainGame.WORLD_WIDTH - border - border),
                border + rand.nextFloat() * (MainGame.WORLD_HEIGHT - border - border));
    }

    /**
     * @return Rectangle object.
     */
    public Rectangle getRect()
    {
        return rect;
    }

    /**
     * Renders the food object on the screen.
     * @param r Used to render the object.
     * @param col The color of the object.
     */
    public void render(ShapeRenderer r, Color col)
    {
        r.setColor(col);
        r.rect(rect.x, rect.y, rect.width, rect.height);
    }
}
