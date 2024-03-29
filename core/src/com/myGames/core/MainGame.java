package com.myGames.core;

import com.myGames.core.Screens.*;
import com.myGames.core.Screens.Pokemon.IntroductionScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * MainGame class. The main class which connects all
 * the game screens together
 */
public class MainGame extends Game
{
	public static final float WORLD_WIDTH = 1500;	// Width of the screen
	public static final float WORLD_HEIGHT = 800;	// Height of the screen

	public MenuScreen menuScreen;					// Screen for main menu
	public BreakoutScreen breakoutScreen;			// Screen for the breakout game
	public PongScreen pongScreen;					// Screen for the pong game
	public SnakeScreen snakeScreen; 				// Screen for the snake game
	public GameOverScreen gameOverScreen;			// Screen for when the game is over
	public SpaceScreen spaceScreen;
	public IntroductionScreen pokemonScreen;

	public SpriteBatch batch;						// Batch to draw on screen
	public ShapeRenderer render;					// Render to render objects on screen
	public BitmapFont font;							// Font of text
	
	@Override
	public void create()
	{
		// Create all the screens
		menuScreen = new MenuScreen(this);
		breakoutScreen = new BreakoutScreen(this);
		pongScreen = new PongScreen(this);
		snakeScreen = new SnakeScreen(this);
		gameOverScreen = new GameOverScreen(this);
		spaceScreen = new SpaceScreen(this);
		pokemonScreen = new IntroductionScreen(this);

		// Create tools to create tex for all screens
		batch = new SpriteBatch();
		render = new ShapeRenderer();
		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.setColor(Color.WHITE);
		font.getData().setScale(2f);

		// Sets the first screen to be the main menu
		setScreen(menuScreen);
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
		render.dispose();
		font.dispose();
	}
}
