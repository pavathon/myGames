package com.myGames.Breakout_Pong.Breakout;

import com.myGames.Breakout_Pong.Player;
import com.myGames.Screens.MainGame;

public class BreakoutPlayer extends Player
{
    public BreakoutPlayer()
    {
        super(130, 20);
        reset();
    }

    @Override
    protected void reset()
    {
        block.setCenter(MainGame.WORLD_WIDTH / 2, 20);
    }

    @Override
    public void checkWorldBorders()
    {
        if (block.x <= 0)
            block.x = 0;
        else if (block.x + getWidth() >= MainGame.WORLD_WIDTH)
            block.x = MainGame.WORLD_WIDTH - getWidth();
    }
}
