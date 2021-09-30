package Screens;

import Breakout_Pong.Ball;
import Breakout_Pong.Pong.PlayerNum;
import Breakout_Pong.Pong.PongPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Screen class which displays the Pong game.
 */
public class PongScreen extends ScreenAdapter
{
	private static final int POINTS_TO_WIN = 4;

	private final MainGame game;

	private PongPlayer player1; 	// Left paddle
	private PongPlayer player2;		// Right paddle
	private Ball ball;				// Ball used in the game
	private int player1Score;		// Score for player1
	private int player2Score;		// Score for player2
	private final Sound ballSound;      // Sound the ball makes when colliding with objects

	public PongScreen(MainGame game)
	{
		this.game = game;
		ballSound = Gdx.audio.newSound(Gdx.files.internal("sounds/plop.ogg"));
	}

	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) game.setScreen(game.menuScreen);
				return true;
			}
		});

		player1 = new PongPlayer(PlayerNum.PLAYER1);
		player2 = new PongPlayer(PlayerNum.PLAYER2);
		ball = new Ball();

		player1Score = player2Score = 0;
	}

	@Override
	public void render(float v)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render all the objects displayed in the screen
		game.render.begin(ShapeRenderer.ShapeType.Filled);
		player1.render(game.render, Color.WHITE);
		player2.render(game.render, Color.WHITE);
		ball.render(game.render);
		game.render.end();

		// Display the score
		game.batch.begin();
		game.font.draw(game.batch, player1Score + "|" + player2Score, MainGame.WORLD_WIDTH / 2, 650);
		game.batch.end();

		// Collision checks
		player1.checkWorldBorders();
		player2.checkWorldBorders();

		PlayerNum player = ball.checkWorldBordersPong();

		// Check if either player1 or player2 scored.
		if (player == PlayerNum.PLAYER1) {
			ball.reset();
			player1Score++;
		}
		else if (player == PlayerNum.PLAYER2) {
			ball.reset();
			player2Score++;
		}

		// Check whether player1 of player2 won the game.
		if (player1Score == POINTS_TO_WIN) {
			game.gameOverScreen.setgameovertxt("Player 1 WON!");
			game.setScreen(game.gameOverScreen);
		}
		else if (player2Score == POINTS_TO_WIN) {
			game.gameOverScreen.setgameovertxt("Player 2 WON!");
			game.setScreen((game.gameOverScreen));
		}

		if (ball.overlaps(player1.getBlock())) ballSound.play();
		else if (ball.overlaps(player2.getBlock())) ballSound.play();

		// Move player blocks
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			player1.move(Input.Keys.W);
		else if (Gdx.input.isKeyPressed(Input.Keys.S))
			player1.move(Input.Keys.S);

		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			player2.move(Input.Keys.UP);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			player2.move(Input.Keys.DOWN);

		ball.move();
	}

	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose()
	{
		ballSound.dispose();
	}
}
