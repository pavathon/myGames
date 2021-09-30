package Breakout_Pong.Pong;

import Breakout_Pong.Player;
import Screens.MainGame;

/**
 * Creates a rectangle that can be controlled by the player for the game Pong.
 * Can only move up or down.
 * Two paddles in a Pong game.
 */
public class PongPlayer extends Player
{
    private static final int PADDING = 25;  // The space between the paddle and the vertical border
    private final PlayerNum player;

    /**
     * Constructor for the PongPlayer class
     * @param player Checks whether it is the left or right player
     */
    public PongPlayer(PlayerNum player)
    {
        super(20, 130);
        this.player = player;
        reset();
    }

    @Override
    protected void reset()
    {
        if (player == PlayerNum.PLAYER1)
            block.setCenter(PADDING + (block.width / 2), MainGame.WORLD_HEIGHT / 2);
        else if (player == PlayerNum.PLAYER2)
            block.setCenter(MainGame.WORLD_WIDTH - PADDING - (block.width / 2), MainGame.WORLD_HEIGHT / 2);
    }

    @Override
    public void checkWorldBorders()
    {
        if (block.y <= 0)
            block.y = 0;
        else if (block.y + getHeight() >= MainGame.WORLD_HEIGHT)
            block.y = MainGame.WORLD_HEIGHT - getHeight();
    }
}
