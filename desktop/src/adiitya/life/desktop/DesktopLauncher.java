package adiitya.life.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import adiitya.life.Main;

public class DesktopLauncher {

	public static void main (String[] arg) {

		new LwjglApplication(new Main(), getConfig());
	}

	private static LwjglApplicationConfiguration getConfig() {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 600;
		config.height = 600;

		return config;
	}
}
