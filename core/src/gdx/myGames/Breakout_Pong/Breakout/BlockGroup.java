package gdx.myGames.Breakout_Pong.Breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import gdx.myGames.Breakout_Pong.Ball;
import gdx.myGames.Breakout_Pong.Block;
import gdx.myGames.MainGame;

/**
 * Groups HitBlock objects together
 */
public class BlockGroup
{
    private final Block[][] blockGroup;       // The array used to store the blocks.
    private final int rows;                   // The amount of rows in the array.
    private final int columns;                // The amount of columns in the array.
    private final int area;

    /**
     * Constructor for the BlockGroup class.
     * @param rows - The amount of rows there are in the array.
     * @param columns - The amount of columns there are in the array.
     */
    public BlockGroup(int rows, int columns)
    {
        blockGroup = new Block[rows][columns];
        this.rows = rows;
        this.columns = columns;
        area = rows * columns;
    }

    /**
     * @return Number of rows in BlockGroup
     */
    public int getRow()
    {
        return rows;
    }

    /**
     * @return Number of columns in BlockGroup
     */
    public int getColumn()
    {
        return columns;
    }

    /**
     * @return Size of array (rows * columns)
     */
    public int getArea()
    {
        return area;
    }

    /**
     * Sets the co-ordinates of each hitBlock in the array.
     * @param b - The hitBlock object to be set.
     * @param rowNum - The row number of the hitBlock object.
     * @param colNum - The column number of the hitBlock object.
     */
    private void setCoordinates(Block b, int rowNum, int colNum)
    {
        int space = 20;
        int starting_x = (int) ((MainGame.WORLD_WIDTH - ((100 * rows) + (space * rows - 1))) / 2);
        int starting_y = 400;

        Rectangle rect = b.getBlock();
        if (rowNum == 0 && colNum == 0) rect.setPosition(starting_x, starting_y);
        else {
            rect.x = starting_x + (space * rowNum) + (b.getWidth() * rowNum);
            rect.y = starting_y + (space * colNum) + (b.getHeight() * colNum);
        }
    }

    /**
     * Creates hitBlock objects and puts them in the array
     */
    public void groupBlocks()
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block b = new Block(100, 40);
                setCoordinates(b, i, j);
                blockGroup[i][j] = b;
            }
        }
    }

    /**
     * Renders all of the hitBlock objects in the array to the screen.
     * @param render - ShapeRenderer object used to render.
     */
    public void renderHitBlocks(ShapeRenderer render)
    {
        Color[] cols = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block b = blockGroup[i][j];
                if (b != null) {
                    b.render(render, cols[j]);
                }
            }
        }
    }

    /**
     * Checks whether any of the hitBlock objects in the array
     * have collided with the ball.
     * @param ball - The ball to be checked.
     * @return Whether a block has been hit by the ball.
     */
    public boolean checkAllOverlaps(Ball ball)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block b = blockGroup[i][j];
                if (b != null) {
                    if (ball.overlaps(b.getBlock())) {
                        blockGroup[i][j] = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
