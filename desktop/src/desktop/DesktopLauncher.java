package desktop;

import Screens.MainGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

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
