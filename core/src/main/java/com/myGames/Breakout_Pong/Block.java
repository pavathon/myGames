package com.myGames.Breakout_Pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Gives all the block-like elements their properties.
 * This includes the player controlled block and the blocks
 * the user is trying to hit.
 */
public class Block
{
    protected Rectangle block;  // Used to make the actual body of the block to make it interactable.

    /**
     * Constructor for the Block class
     * @param width - The width of the block.
     * @param height - The height of the block.
     */
    public Block(int width, int height)
    {
        block = new Rectangle();
        block.width = width;
        block.height = height;
    }

    /**
     * @return The width of the block
     */
    public int getWidth()
    {
        return (int) block.width;
    }

    /**
     * @return The height of the block
     */
    public int getHeight()
    {
        return (int) block.height;
    }

    /**
     * @return The Rectangle (interactable) element of the Block
     */
    public Rectangle getBlock()
    {
        return block;
    }

    /**
     * Renders the Block object to the screen
     */
    public void render(ShapeRenderer r, Color col)
    {
        r.setColor(col);
        r.rect(block.x, block.y, block.width, block.height);
    }
}
