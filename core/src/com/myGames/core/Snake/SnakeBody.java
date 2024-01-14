package com.myGames.core.Snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class SnakeBody
{
    protected final Rectangle rect;

    public SnakeBody(float x, float y, float width, float height)
    {
        rect = new Rectangle(x, y, width, height);
    }

    public Rectangle getRect()
    {
        return rect;
    }

    public void render(ShapeRenderer r, Color col)
    {
        r.setColor(col);
        r.rect(rect.x, rect.y, rect.width, rect.height);
    }
}
