package gdx.myGames.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.myGames.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.title = "Breakout";
		config.width = (int) MainGame.WORLD_WIDTH;
		config.height = (int) MainGame.WORLD_HEIGHT;
		new LwjglApplication(new MainGame(), config);
	}
}
